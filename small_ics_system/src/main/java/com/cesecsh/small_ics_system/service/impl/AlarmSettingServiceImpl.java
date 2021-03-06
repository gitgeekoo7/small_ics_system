package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.dto.TbAlarmSettingDto;
import com.cesecsh.small_ics_system.mapper.AlarmSettingMapper;
import com.cesecsh.small_ics_system.model.TbAlarmSetting;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.service.IAlarmSettingService;
import com.cesecsh.small_ics_system.util.DelFlag;
import com.cesecsh.small_ics_system.util.RunningState;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AlarmSettingServiceImpl implements IAlarmSettingService {
    @Autowired
    private AlarmSettingMapper alarmSettingMapper;

    @Override
    public void saveSetting(TbAlarmSetting setting) {
        setting.setId(UUID.randomUUID().toString().replace("-", ""));
        setting.setDelFlag(DelFlag.UN_DELETED.getKey());
        setting.setCreateTime(new Date());
        setting.setUpdateTime(new Date());
        alarmSettingMapper.insertSetting(setting);
        //todo 下发报警设置
    }

    @Override
    public void deleteSetting(String id) {
        TbAlarmSetting setting = new TbAlarmSetting();
        setting.setId(id);
        setting.setDelFlag(DelFlag.DELETED.getKey());
        setting.setUpdateTime(new Date());
        alarmSettingMapper.deleteSetting(setting);
        //todo 清除报警设置
    }

    @Override
    public void updateSetting(TbAlarmSetting setting) {
        setting.setUpdateTime(new Date());
        alarmSettingMapper.updateSetting(setting);
        //todo 下发报警设置 同一个输入端口的是否覆盖？
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<TbAlarmSettingDto> listSetting(QueryObject queryObject) {
        queryObject.setDelFlag(DelFlag.UN_DELETED.getKey());
        List<TbAlarmSettingDto> settings = alarmSettingMapper.listSetting(queryObject);
        for (TbAlarmSetting setting : settings) {
            String value = RunningState.getValueByKey(setting.getLinkageAction());
            setting.setLinkageAction(value);
        }
        return new PageInfo<>(settings);
    }

    @Override
    @Transactional(readOnly = true)
    public TbAlarmSetting getSetting(String id) {
        return alarmSettingMapper.getSetting(id, DelFlag.UN_DELETED.getKey());
    }
}
