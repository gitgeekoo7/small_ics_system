package com.cesecsh.small_ics_system.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbIcsSocketVo {
    private String serial;//序列号
    private String ip;//ics自身ip
    private String serverIp;//服务器ip
    private String submask;//子网掩码
    private String gateway;//网关
    private String version;//嵌入式版本
}
