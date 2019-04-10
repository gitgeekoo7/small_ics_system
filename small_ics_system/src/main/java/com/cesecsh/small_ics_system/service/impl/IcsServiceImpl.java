package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.mapper.IcsMapper;
import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.QueryObject;
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

    @Override
    public void saveIcs(TbIcs ics) {
        ics.setId(UUID.randomUUID().toString().replace("-", ""));
        ics.setDelFlag(DelFlag.UN_DELETED.getKey());
        ics.setCreateTime(new Date());
        ics.setUpdateTime(new Date());
        icsMapper.insertIcs(ics);
    }

    @Override
    public void deleteIcs(String id) {
        TbIcs ics = new TbIcs();
        ics.setId(id);
        ics.setDelFlag(DelFlag.DELETED.getKey());
        ics.setUpdateTime(new Date());
        icsMapper.deleteIcs(ics);
    }

    @Override
    public void updateIcs(TbIcsVo vo) throws Exception {
        TbIcs ics = icsMapper.getIcs(vo.getId(), null);
        BeanUtils.copyProperties(ics, vo);
        ics.setUpdateTime(new Date());
        icsMapper.updateIcs(ics);
        //IP修改，给下位机下发修改IP指令，硬件后行--如果修改失败可以回滚
        if (!ics.getIp().equals(vo.getIp()) || !ics.getServerIp().equals(vo.getServerIp()) ||
                !ics.getGateway().equals(vo.getGateway()) || !ics.getSubmask().equals(vo.getSubmask())) {
            //todo 调用修改IP接口
        }
    }

    @Override
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
    public TbIcs getIcs(String id) {
        return icsMapper.getIcs(id, DelFlag.UN_DELETED.getKey());
    }
}
