package com.cesecsh.small_ics_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class TbDacChannelPower {
    private String id;//
    private String icsId;//ics控制器id
    private String dacId;//采控器id
    private String dacChannelId;//采控器线路/通道id
    private String dacChannelType;//传感器类型
    private String value;//设备值
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    private String state;//状态(保留)
    private String name;//设备名称(保留)
    private Date updateTime;//修改时间
}
