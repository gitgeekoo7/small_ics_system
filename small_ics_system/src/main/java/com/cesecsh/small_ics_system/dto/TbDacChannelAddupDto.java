package com.cesecsh.small_ics_system.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TbDacChannelAddupDto {
    private String id;
    private String name;
    private List<AddupDto> powers;
}
