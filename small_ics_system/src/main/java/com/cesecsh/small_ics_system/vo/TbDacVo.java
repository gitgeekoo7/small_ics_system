package com.cesecsh.small_ics_system.vo;

import java.util.List;

import com.cesecsh.small_ics_system.model.TbDacChannel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbDacVo {
	
	private String id;//主键
	private String name;//采控器名称
	private String detail;//详细信息
    private String remark;//备注
    
	private List<TbDacChannel> channelList;//采控器通道
}
