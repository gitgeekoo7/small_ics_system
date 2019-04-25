package com.cesecsh.small_ics_system.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 报警设置
 */
@Setter
@Getter
public class TbAlarmSetting extends BaseObject {
    private String name;//名称
    private String icsId;//关联的ics
    private String input;//输入端口
    private Integer threshold;//阈值
    private String output;//输出端口
    private String dacId;//联动设备
    private String dacChannelId;//联动设备通道/线路
    private String linkageAction;//联动动作：0关  1开
}
