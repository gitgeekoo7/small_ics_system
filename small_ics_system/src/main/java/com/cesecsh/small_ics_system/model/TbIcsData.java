package com.cesecsh.small_ics_system.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbIcsData extends BaseObject {
    private String icsId;
    private String data;
    private String name;
    private String unit;
}
