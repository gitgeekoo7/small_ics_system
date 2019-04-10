package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.IcsQueryObject;
import com.cesecsh.small_ics_system.util.WorkingState;
import com.cesecsh.small_ics_system.vo.TbIcsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IcsServiceTests {
    @Autowired
    private IIcsService icsService;

    @Test
    public void textSave() {
        TbIcs ics = new TbIcs();
        ics.setCode("10000");
        ics.setName("测试ics");
        ics.setSerial("88880000");
        ics.setIp("192.168.0.112");
        ics.setServerIp("192.168.0.172");
        ics.setGateway("192.168.0.1");
        ics.setSubmask("255.255.255.0");
        ics.setState(WorkingState.ON_LINE.getKey());
        ics.setRemark("没什么需要备注的");
        ics.setVersion("v1.0.0");
        icsService.saveIcs(ics);
    }

    @Test
    public void testDelete() {
        icsService.deleteIcs("3fdb438e5ee64a0db7449733e299b869");
    }

    @Test
    public void testUpdate() throws Exception {
        TbIcsVo ics = new TbIcsVo();
        ics.setId("3fdb438e5ee64a0db7449733e299b869");
        ics.setCode("20000");
        ics.setName("测试ics编辑");
        ics.setIp("192.168.0.222");
        ics.setServerIp("192.168.0.172");
        ics.setGateway("192.168.0.1");
        ics.setSubmask("255.255.255.0");
        ics.setRemark("编辑测试");
        icsService.updateIcs(ics);
    }

    @Test
    public void testList() throws Exception {
        IcsQueryObject queryObject = new IcsQueryObject();
        queryObject.setPageSize(10);
        queryObject.setPageNum(1);
        PageInfo<TbIcs> pageInfo = icsService.listIcs(queryObject);
        System.out.println(new ObjectMapper().writeValueAsString(pageInfo));
    }

    @Test
    public void testGet() throws Exception {
        TbIcs ics = icsService.getIcs("3fdb438e5ee64a0db7449733e299b869");
        System.out.println(new ObjectMapper().writeValueAsString(ics));
    }
}
