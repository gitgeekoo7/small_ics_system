package com.cesecsh.small_ics_system.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 报警设置
 */
@Setter
@Getter
public class TbAlarmSetting extends BaseObject {
    private String icsId;//关联的ics
    private String inputPort;//输入端口
    private String trigger;//触发条件：0低电平  1高电平
    private String outputPort;//输出端口
    private String alarmAction;//报警动作：0关  1开
    private String dacId;//联动设备
    private String dacChannelId;//联动设备通道/线路
    private String linkageAction;//联动动作：0关  1开
}
