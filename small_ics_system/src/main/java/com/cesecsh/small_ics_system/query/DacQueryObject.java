package com.cesecsh.small_ics_system.query;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DacQueryObject extends QueryObject {
    private String name;//采控器名称
    private String address;//采控器地址
    private String channel;//通道号
    private String state;//设备状态
    private String channelName;//线路名称
    private String code;//ics编号

    private String enable;
}
