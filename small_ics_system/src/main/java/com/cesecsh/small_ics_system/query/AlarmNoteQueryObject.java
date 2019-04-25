package com.cesecsh.small_ics_system.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlarmNoteQueryObject extends QueryObject {
    private String dacChannelName;
    private String icsName;
}
