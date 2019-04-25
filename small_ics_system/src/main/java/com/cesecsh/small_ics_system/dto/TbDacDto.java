package com.cesecsh.small_ics_system.dto;

import com.cesecsh.small_ics_system.model.TbDac;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbDacDto extends TbDac {
    private String icsName;
    private String icsIp;
}
