package com.cesecsh.small_ics_system.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 控制器ics
 */
@Setter
@Getter
public class TbIcs extends BaseObject {
    private String name;//名称
    private String serial;//序列号
    private String code;//设备编号
    private String ip;//ics自身ip
    private String serverIp;//服务器ip
    private String submask;//子网掩码
    private String gateway;//网关
    private String state;//状态：0在线 1离线
    private String version;//嵌入式版本
    private String remark;//备注

    private String data;
}
