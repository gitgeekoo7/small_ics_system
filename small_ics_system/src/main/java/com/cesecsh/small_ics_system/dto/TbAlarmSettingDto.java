package com.cesecsh.small_ics_system.dto;

import com.cesecsh.small_ics_system.model.TbAlarmSetting;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbAlarmSettingDto extends TbAlarmSetting {
    private String icsName;
    private String dacName;
    private String dacChannelName;
}
