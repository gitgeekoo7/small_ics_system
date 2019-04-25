package com.cesecsh.small_ics_system.vo;

import com.cesecsh.small_ics_system.model.TbDacChannel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbDacChannelVo extends TbDacChannel {
    private String dacName;//采控器名称
    private String address;//采控器地址
    private String icsName;//ics名称
    private String icsState;//ics状态
}
