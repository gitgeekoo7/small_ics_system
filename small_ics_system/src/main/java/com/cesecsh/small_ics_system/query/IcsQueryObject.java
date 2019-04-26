package com.cesecsh.small_ics_system.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IcsQueryObject extends QueryObject {
    private String name;
    private String ip;

    private String serial;
}
