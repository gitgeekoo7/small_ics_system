package com.cesecsh.small_ics_system.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbIcsVo {
    private String id;//主键
    private String name;//名称
    private String code;//设备编号
    private String ip;//ics自身ip
    private String serverIp;//服务器ip
    private String submask;//子网掩码
    private String gateway;//网关
    private String remark;//备注
}
