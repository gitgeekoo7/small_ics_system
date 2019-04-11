package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.dto.TbAlarmSettingDto;
import com.cesecsh.small_ics_system.mapper.AlarmSettingMapper;
import com.cesecsh.small_ics_system.model.TbAlarmSetting;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.service.IAlarmSettingService;
import com.cesecsh.small_ics_system.util.DelFlag;
import com.cesecsh.small_ics_system.util.RunningState;
import com.cesecsh.small_ics_system.util.TriggerCondition;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
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
    }

    @Override
    public void deleteSetting(String id) {
        TbAlarmSetting setting = new TbAlarmSetting();
        setting.setId(id);
        setting.setDelFlag(DelFlag.DELETED.getKey());
        setting.setUpdateTime(new Date());
        alarmSettingMapper.deleteSetting(setting);
    }

    @Override
    public void updateSetting(TbAlarmSetting setting) {
        setting.setUpdateTime(new Date());
        alarmSettingMapper.updateSetting(setting);
    }

    @Override
    public PageInfo<TbAlarmSettingDto> listSetting(QueryObject queryObject) {
        queryObject.setDelFlag(DelFlag.UN_DELETED.getKey());
        List<TbAlarmSettingDto> settings = alarmSettingMapper.listSetting(queryObject);
        for (TbAlarmSetting setting : settings) {
            String value = TriggerCondition.getValueByKey(setting.getTrigger());
            setting.setTrigger(value);
            value = RunningState.getValueByKey(setting.getAlarmAction());
            setting.setAlarmAction(value);
            value = RunningState.getValueByKey(setting.getLinkageAction());
            setting.setLinkageAction(value);
        }
        return new PageInfo<>(settings);
    }

    @Override
    public TbAlarmSetting getSetting(String id) {
        return alarmSettingMapper.getSetting(id, DelFlag.UN_DELETED.getKey());
    }
}
