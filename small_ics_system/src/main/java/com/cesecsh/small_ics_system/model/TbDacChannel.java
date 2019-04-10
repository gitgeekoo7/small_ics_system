package com.cesecsh.small_ics_system.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 采控器线路
 */
@Setter
@Getter
public class TbDacChannel {
    private String id;//主键
    private String channel;//通道
    private String name;//名称
    private String type;//类型
    private String enable;//启用标志：0禁用 1启用
    private String state;//状态：0关 1开
    private String data;//实时数据
    private String dacId;//所属采控器id
}
