package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.dto.TbDacChannelAddupDto;
import com.cesecsh.small_ics_system.vo.TbDacChannelPowerVo;

import java.util.List;

public interface IDacChannelPowerService {
    void savePower(TbDacChannelPowerVo vo);

    List<TbDacChannelAddupDto> addupPower(String group);
}
