package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.mapper.DacChannelMapper;
import com.cesecsh.small_ics_system.mapper.DacMapper;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbDacChannel;
import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.IcsQueryObject;
import com.cesecsh.small_ics_system.util.WorkingState;
import com.cesecsh.small_ics_system.vo.TbIcsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 采控器单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DacServiceTests {
	 @Autowired
	 private IDacService dacService;

    @Test
    public void textSave() {
    	
        TbDac dac = new TbDac();
        dac.setOrderNumber("01");
        dac.setAddress("151");
        dac.setName("1号楼20层401灯控");
        dac.setDetail("弱电井311");
        dac.setRemark("测试");
        dac.setIcsId("3fdb438e5ee64a0db7449733e299b869");
        List<TbDacChannel> channelList = new ArrayList<>();
        for(int i=0;i<4;i++) {
        	TbDacChannel dacChannel = new TbDacChannel();
        	dacChannel.setChannel(String.valueOf(i++));
        	dacChannel.setName("test");
        	dacChannel.setType("0");
        	dacChannel.setEnable("0");
        	dacChannel.setState("0");
        	dacChannel.setData("1A");
        	channelList.add(dacChannel);
        }
        dac.setChannelList(channelList);
        dacService.saveDac(dac);
    }

    /*@Test
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
    }*/
}
