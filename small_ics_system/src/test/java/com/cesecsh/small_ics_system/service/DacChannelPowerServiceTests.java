package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.vo.TbDacChannelPowerVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DacChannelPowerServiceTests {
    @Autowired
    private IDacChannelPowerService dacChannelPowerService;

    @Test
    public void textSave() {
        TbDacChannelPowerVo power = new TbDacChannelPowerVo();
        power.setSerial("88880000");
        power.setAddress("151");
        power.setChannel("1");
        power.setPower("9972");
        dacChannelPowerService.savePower(power);
    }
}
