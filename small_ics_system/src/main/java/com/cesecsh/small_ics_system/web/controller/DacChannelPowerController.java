package com.cesecsh.small_ics_system.web.controller;

import com.cesecsh.small_ics_system.dto.TbDacChannelAddupDto;
import com.cesecsh.small_ics_system.query.DacChannelPowerQueryObject;
import com.cesecsh.small_ics_system.service.IDacChannelPowerService;
import com.cesecsh.small_ics_system.util.Result;
import com.cesecsh.small_ics_system.vo.TbDacChannelPowerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dac/channel/power")
public class DacChannelPowerController {
    @Autowired
    private IDacChannelPowerService dacChannelPowerService;

    @RequestMapping("/save")
    public Result savePower(@RequestBody TbDacChannelPowerVo power) {
        dacChannelPowerService.savePower(power);
        return Result.success();
    }

    @RequestMapping("/addup")
    public Result addupPower(@RequestBody DacChannelPowerQueryObject queryObject) {
        List<TbDacChannelAddupDto> powerAddup = dacChannelPowerService.addupPower(queryObject.getGroup());
        return Result.success(powerAddup);
    }
}
