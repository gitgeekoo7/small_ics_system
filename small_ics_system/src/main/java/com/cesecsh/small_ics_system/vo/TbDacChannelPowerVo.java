package com.cesecsh.small_ics_system.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbDacChannelPowerVo {
    private String serial;//ics序列号
    private String address;//采集器地址
    private String channel;//传感器通道

    private String power;
}
