package com.cesecsh.small_ics_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 报警记录
 */
@Setter
@Getter
public class TbAlarmNote {
    private String id;
    private String icsId;//ics id
    private String icsName;//ics名称
    private String input;//输入
    private Integer threshold;//阈值
    private String output;//输出
    private String dacId;//联动设备id
    private String dacName;//联动的采控器名称
    private String dacChannelId;//联动设备通道/线路
    private String dacChannelName;//联动的采控器线路/通道名称
    private String linkageAction;//联动动作
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alarmTime;//报警时间
    private String alarmSettingId;//所属报警设置id
    private String alarm_setting_name;//报警设置名称
}