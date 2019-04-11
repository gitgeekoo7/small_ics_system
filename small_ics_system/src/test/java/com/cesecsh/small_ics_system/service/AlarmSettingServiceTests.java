package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.dto.TbAlarmSettingDto;
import com.cesecsh.small_ics_system.model.TbAlarmSetting;
import com.cesecsh.small_ics_system.query.AlarmSettingQueryObject;
import com.cesecsh.small_ics_system.util.RunningState;
import com.cesecsh.small_ics_system.util.TriggerCondition;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmSettingServiceTests {
    @Autowired
    private IAlarmSettingService alarmSettingService;

    @Test
    public void textSave() {
        TbAlarmSetting setting = new TbAlarmSetting();
        setting.setIcsId("3fdb438e5ee64a0db7449733e299b869");
        setting.setInputPort("01");
        setting.setTrigger(TriggerCondition.HIGH_LEVEL.getKey());
        setting.setOutputPort("01");
        setting.setAlarmAction(RunningState.OPEN.getKey());
        setting.setDacId("7006520f509d4b67924eefef43f01297");
        setting.setDacChannelId("689d1dca1e45401fa59e2e21dcce4b94");
        setting.setLinkageAction(RunningState.OPEN.getKey());
        alarmSettingService.saveSetting(setting);
    }

    @Test
    public void testDelete() {
        alarmSettingService.deleteSetting("7c733b3ad81b4de39e3d776b370d565a");
    }

    @Test
    public void testUpdate() throws Exception {
        TbAlarmSetting setting = new TbAlarmSetting();
        setting.setId("7c733b3ad81b4de39e3d776b370d565a");
        setting.setIcsId("3fdb438e5ee64a0db7449733e299b869");
        setting.setInputPort("02");
        setting.setTrigger(TriggerCondition.LOW_LEVEL.getKey());
        setting.setOutputPort("02");
        setting.setAlarmAction(RunningState.CLOSE.getKey());
        setting.setDacId("7006520f509d4b67924eefef43f01297");
        setting.setDacChannelId("8d5fcd73ce24483e88b8fb0197028bda");
        setting.setLinkageAction(RunningState.OPEN.getKey());
        alarmSettingService.updateSetting(setting);
    }

    @Test
    public void testList() throws Exception {
        AlarmSettingQueryObject queryObject = new AlarmSettingQueryObject();
        queryObject.setPageSize(10);
        queryObject.setPageNum(1);
        PageInfo<TbAlarmSettingDto> pageInfo = alarmSettingService.listSetting(queryObject);
        System.out.println(new ObjectMapper().writeValueAsString(pageInfo));
    }

    @Test
    public void testGet() throws Exception {
        TbAlarmSetting setting = alarmSettingService.getSetting("7c733b3ad81b4de39e3d776b370d565a");
        System.out.println(new ObjectMapper().writeValueAsString(setting));
    }
}
