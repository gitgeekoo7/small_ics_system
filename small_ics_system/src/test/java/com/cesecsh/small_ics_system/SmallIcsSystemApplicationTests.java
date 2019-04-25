package com.cesecsh.small_ics_system;

import com.cesecsh.small_ics_system.vo.TbIcsSocketVo;
import com.cesecsh.small_ics_system.web.socket.IcsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmallIcsSystemApplicationTests {

    @Test
    public void testSocketSaveIcs() {
        TbIcsSocketVo vo = new TbIcsSocketVo();
        vo.setSerial("77777777");
        vo.setIp("192.168.0.111");
        vo.setServerIp("192.168.0.172");
        vo.setGateway("192.168.0.1");
        vo.setSubmask("255.255.255.0");
        vo.setVersion("v1.0.1-2.5.0");
        IcsService.saveIcs(vo);
    }
}
