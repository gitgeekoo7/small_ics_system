package com.cesecsh.small_ics_system.web.controller;

import com.cesecsh.small_ics_system.dto.TbAlarmSettingDto;
import com.cesecsh.small_ics_system.model.TbAlarmSetting;
import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.AlarmSettingQueryObject;
import com.cesecsh.small_ics_system.service.IAlarmSettingService;
import com.cesecsh.small_ics_system.util.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm/setting")
public class AlarmSettingController {
    @Autowired
    private IAlarmSettingService alarmSettingService;

    @RequestMapping("save")
    public Result saveSetting(@RequestBody TbAlarmSetting setting) {
        alarmSettingService.saveSetting(setting);
        return Result.success();
    }

    @RequestMapping("delete")
    public Result deleteSetting(@RequestBody TbAlarmSetting setting) {
        alarmSettingService.deleteSetting(setting.getId());
        return Result.success();
    }

    @RequestMapping("update")
    public Result updateIcs(@RequestBody TbAlarmSetting setting) {
        alarmSettingService.updateSetting(setting);
        return Result.success();
    }

    @RequestMapping("list")
    @Transactional(readOnly = true)
    public Result listSetting(@RequestBody AlarmSettingQueryObject queryObject) {
        PageInfo<TbAlarmSettingDto> pageInfo = alarmSettingService.listSetting(queryObject);
        return Result.success(pageInfo);
    }

    @RequestMapping("get")
    @Transactional(readOnly = true)
    public Result getSetting(@RequestBody AlarmSettingQueryObject queryObject) {
        TbAlarmSetting setting = alarmSettingService.getSetting(queryObject.getId());
        return Result.success(setting);
    }
}
