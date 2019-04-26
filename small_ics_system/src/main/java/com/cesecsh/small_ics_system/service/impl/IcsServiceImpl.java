package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.mapper.IcsMapper;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.model.TbIcsData;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.service.IDacService;
import com.cesecsh.small_ics_system.service.IIcsDataService;
import com.cesecsh.small_ics_system.service.IIcsService;
import com.cesecsh.small_ics_system.socket.util.IcsMsgUtil;
import com.cesecsh.small_ics_system.util.DelFlag;
import com.cesecsh.small_ics_system.util.WorkingState;
import com.cesecsh.small_ics_system.vo.TbIcsVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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
            throw new RuntimeException("ICS不存在");
        }
        TbIcs ics = new TbIcs();
        BeanUtils.copyProperties(ics, vo);
        ics.setUpdateTime(new Date());
        icsMapper.updateIcs(ics);
        //IP修改，给下位机下发修改IP指令，硬件后行--如果修改失败可以回滚
        if (!icsInDb.getIp().equals(ics.getIp()) || !icsInDb.getServerIp().equals(ics.getServerIp()) ||
                !icsInDb.getGateway().equals(ics.getGateway()) || !icsInDb.getSubmask().equals(ics.getSubmask())) {
            if (WorkingState.OFF_LINE.getKey().equals(icsInDb.getState())) {
                throw new RuntimeException("ICS不在线,无法修改IP");
            }
            if (!IcsMsgUtil.changeIcsIp(icsInDb.getSerial(), ics.getServerIp(), ics.getIp(), ics.getGateway(), ics.getSubmask())) {
                throw new RuntimeException("修改IP指令发送失败");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<TbIcs> listIcs(QueryObject queryObject) {
        queryObject.setDelFlag(DelFlag.UN_DELETED.getKey());
        List<TbIcs> list = icsMapper.listIcs(queryObject);
        for (TbIcs ics : list) {
            TbIcsData data = icsDataService.getData(ics.getId());
            if (StringUtils.isNotBlank(data.getData())) {
                if ("error".equals(data.getData())) {
                    ics.setData("错误!!!");
                } else {
                    if (StringUtils.isNotBlank(data.getName())) {
                        ics.setData(data.getName() + ":" + data.getData() + data.getUnit());
                    } else {
                        ics.setData(data.getData());
                    }
                }
            }
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

    @Override
    public void saveData(String serial, String value) {
        //获取ICS信息
        TbIcs ics = icsMapper.getIcsBySerial(serial, DelFlag.UN_DELETED.getKey());
        if (null == ics) {
            throw new RuntimeException("ICS不存在");
        }
        //获取输入配置
        TbIcsData data = icsDataService.getData(ics.getId());
        //获取实际值
        String check = icsDataService.getCheckValueByIcsDataIdAndReciveValue(data.getId(), value);
        //保存实时数据
        icsDataService.saveDataValue(data.getId(), check);
    }

    @Override
    public void restartIcs(String serial) {
        TbIcs ics = icsMapper.getIcsBySerial(serial, DelFlag.UN_DELETED.getKey());
        if (null == ics) {
            throw new RuntimeException("ICS不存在");
        }
        if (WorkingState.OFF_LINE.getKey().equals(ics.getState())) {
            throw new RuntimeException("ICS不在线,无法重启");
        }
        if (!IcsMsgUtil.icsChongQi(serial)) {
            throw new RuntimeException("重启指令发送失败");
        }
    }

    @Override
    public void timingIcs(String serial) {
        TbIcs ics = icsMapper.getIcsBySerial(serial, DelFlag.UN_DELETED.getKey());
        if (null == ics) {
            throw new RuntimeException("ICS不存在");
        }
        if (WorkingState.OFF_LINE.getKey().equals(ics.getState())) {
            throw new RuntimeException("ICS不在线,无法校时");
        }
        if (!IcsMsgUtil.wangLuoJiaoShi(serial)) {
            throw new RuntimeException("校时指令发送失败");
        }
    }

    @Override
    public void updateIcsState(String serial, String state) {
        TbIcs ics = icsMapper.getIcsBySerial(serial, DelFlag.UN_DELETED.getKey());
        if (null == ics) {
            throw new RuntimeException("ICS不存在");
        }
        icsMapper.updateState(ics.getId(), state);
    }
}
