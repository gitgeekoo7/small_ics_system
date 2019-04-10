package com.cesecsh.small_ics_system.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 采集控制器dac
 */
@Setter
@Getter
public class TbDac extends BaseObject {
    private String orderNumber;//序号
    private String address;//地址
    private String name;//名称
    private String detail;//详细信息
    private String remark;//备注
    private String icsId;//所属ics
    
    private List<TbDacChannel> channelList;//采控器通道
}
