package com.cesecsh.small_ics_system.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 报警设置
 */
@Setter
@Getter
public class TbAlarmSetting extends BaseObject {
    private String ics_id;//关联的ics
    private String input_port;//输入端口
    private String trigger;//触发条件：0低电平  1高电平
    private String output_port;//输出端口
    private String alarm_action;//报警动作：0关  1开
    private String dac_id;//联动设备
    private String dac_channel_id;//联动设备通道/线路
    private String linkage_action;//联动动作：0关  1开
}
