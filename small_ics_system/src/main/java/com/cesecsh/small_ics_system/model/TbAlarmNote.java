package com.cesecsh.small_ics_system.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 报警记录
 */
@Setter
@Getter
public class TbAlarmNote {
	
    private String id;
    private String icsId;//ics id
    private String icsName;//ics名称
    private String inputPort;//输入端口
    private String trigger;//触发条件：0低电平  1高电平
    private String outputPort;//输出端口
    private String alarmAction;//报警动作
    private String dacId;//联动设备id
    private String dacName;//联动的采控器名称
    private String dacChannelId;//联动设备通道/线路
    private String dacChannelName;//联动的采控器线路/通道名称
    private String linkageAction;//联动动作
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date alarmTime;//报警时间
    private String alarmSettingId;//所属报警设置id
}