package com.cesecsh.small_ics_system.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlarmSettingQueryObject extends QueryObject {
    private String icsName;
    private String dacName;
    private String dacChannelName;

    private String input;
    private String output;
}
