package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.dto.TbAlarmSettingDto;
import com.cesecsh.small_ics_system.model.TbAlarmSetting;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.github.pagehelper.PageInfo;

public interface IAlarmSettingService {
    void saveSetting(TbAlarmSetting setting);

    void deleteSetting(String id);

    void updateSetting(TbAlarmSetting setting);

    PageInfo<TbAlarmSettingDto> listSetting(QueryObject queryObject);

    TbAlarmSetting getSetting(String id);
}
