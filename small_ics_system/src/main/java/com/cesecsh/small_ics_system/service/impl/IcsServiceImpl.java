package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.mapper.IcsMapper;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.model.TbIcsData;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.service.IDacService;
import com.cesecsh.small_ics_system.service.IIcsDataService;
import com.cesecsh.small_ics_system.service.IIcsService;
import com.cesecsh.small_ics_system.util.DelFlag;
import com.cesecsh.small_ics_system.util.WorkingState;
import com.cesecsh.small_ics_system.vo.TbIcsVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class IcsServiceImpl implements IIcsService {
    @Autowired
    private IcsMapper icsMapper;

    @Autowired
    private IDacService dacService;
    @Autowired
    private IIcsDataService icsDataService;

    @Override
    public void saveIcs(TbIcs ics) {
        TbIcs icsInDb = icsMapper.getIcsBySerial(ics.getSerial(), DelFlag.UN_DELETED.getKey());
        ics.setState(WorkingState.ON_LINE.getKey());
        Date date = new Date();
        ics.setUpdateTime(date);
        if (null == icsInDb) {
            //新ICS信息
            ics.setId(UUID.randomUUID().toString().replace("-", ""));
            ics.setDelFlag(DelFlag.UN_DELETED.getKey());
            ics.setCreateTime(date);
            icsMapper.insertIcs(ics);

            //数据信息配置
            TbIcsData data = new TbIcsData();
            data.setIcsId(ics.getId());
            icsDataService.saveData(data);
            //采控器信息
            TbDac dac = new TbDac();
            dac.setIcsId(ics.getId());

            dac.setName("采控器01");
            dac.setOrderNumber("01");
            dac.setAddress("96");
            dacService.saveDac(dac);

            dac.setName("采控器02");
            dac.setOrderNumber("02");
            dac.setAddress("97");
            dacService.saveDac(dac);
        } else {
            //已经存在的控制器信息
            ics.setId(icsInDb.getId());
            icsMapper.insertToUpdateIcs(ics);
        }
    }

    @Override
    public void deleteIcs(String id) {
        //删除ics下的采集控制器
        List<TbDac> dacs = dacService.listDacByIcsId(id);
        for (TbDac dac : dacs) {
            dacService.deleteDac(dac.getId());
        }
        //删除ics
        TbIcs ics = new TbIcs();
        ics.setId(id);
        ics.setDelFlag(DelFlag.DELETED.getKey());
        ics.setUpdateTime(new Date());
        icsMapper.deleteIcs(ics);
    }

    @Override
    public void updateIcs(TbIcsVo vo) throws Exception {
        TbIcs icsInDb = icsMapper.getIcs(vo.getId(), DelFlag.UN_DELETED.getKey());
        if (null == icsInDb) {
            throw new RuntimeException("该ICS不存在");
        }
        TbIcs ics = new TbIcs();
        BeanUtils.copyProperties(ics, vo);
        ics.setUpdateTime(new Date());
        icsMapper.updateIcs(ics);
        //IP修改，给下位机下发修改IP指令，硬件后行--如果修改失败可以回滚
        if (!icsInDb.getIp().equals(ics.getIp()) || !icsInDb.getServerIp().equals(ics.getServerIp()) ||
                !icsInDb.getGateway().equals(ics.getGateway()) || !icsInDb.getSubmask().equals(ics.getSubmask())) {
            //todo 调用修改IP接口
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<TbIcs> listIcs(QueryObject queryObject) {
        queryObject.setDelFlag(DelFlag.UN_DELETED.getKey());
        List<TbIcs> list = icsMapper.listIcs(queryObject);
        for (TbIcs ics : list) {
            String value = WorkingState.getValueByKey(ics.getState());
            ics.setState(value);
        }
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(readOnly = true)
    public TbIcs getIcs(String id) {
        return icsMapper.getIcs(id, DelFlag.UN_DELETED.getKey());
    }
}
