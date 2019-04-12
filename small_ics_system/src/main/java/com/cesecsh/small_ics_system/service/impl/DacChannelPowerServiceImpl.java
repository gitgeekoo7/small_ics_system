package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.dto.TbDacChannelAddupDto;
import com.cesecsh.small_ics_system.mapper.*;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbDacChannel;
import com.cesecsh.small_ics_system.model.TbDacChannelPower;
import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.service.IDacChannelPowerService;
import com.cesecsh.small_ics_system.util.AddupGroupByType;
import com.cesecsh.small_ics_system.util.DelFlag;
import com.cesecsh.small_ics_system.util.EnableFlag;
import com.cesecsh.small_ics_system.vo.TbDacChannelPowerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DacChannelPowerServiceImpl implements IDacChannelPowerService {
    @Autowired
    private DacChannelPowerMapper dacChannelPowerMapper;
    @Autowired
    private IcsMapper icsMapper;
    @Autowired
    private DacMapper dacMapper;
    @Autowired
    private DacChannelMapper dacChannelMapper;
    @Autowired
    private ValueCheckMapper valueCheckMapper;

    @Override
    public void savePower(TbDacChannelPowerVo vo) {
        //目标：将上传的power值（vo.getPower()）转换为能耗值
        //1.获取ics控制id
        TbIcs ics = icsMapper.getIcsBySerial(vo.getSerial());
        if (null == ics) {
            throw new RuntimeException("控制器不存在");
        }
        if (DelFlag.DELETED.getKey().equals(ics.getDelFlag())) {
            throw new RuntimeException("控制器已被删除");
        }

        //2.获取dac采集器id
        TbDac dac = dacMapper.getDacByIcsIdAndAddress(ics.getId(), vo.getAddress());
        if (null == dac) {
            throw new RuntimeException("采集器不存在");
        }
        if (DelFlag.DELETED.getKey().equals(dac.getDelFlag())) {
            throw new RuntimeException("采集器已被删除");
        }

        //3.获取线路/通道信息
        TbDacChannel channel = dacChannelMapper.getDacChannelByDacIdAndChannel(dac.getId(), vo.getChannel());
        if (null == channel) {
            throw new RuntimeException("通道不存在");
        }
        if (EnableFlag.DISABLE.getKey().equals(channel.getEnable())) {
            throw new RuntimeException("通道已被禁用");
        }

        //获取能耗值
        String value = valueCheckMapper.getValueByRatioAndResistanceAndPower(channel.getCheckRatio(), channel.getCheckResistance(), vo.getPower());

        //能耗生成记录信息
        TbDacChannelPower power = new TbDacChannelPower();
        power.setId(UUID.randomUUID().toString().replace("-", ""));
        power.setIcsId(ics.getId());
        power.setDacId(dac.getId());
        power.setDacChannelId(channel.getId());
        power.setDacChannelType(channel.getType());
        power.setValue(value);
        power.setCreateTime(new Date());
        dacChannelPowerMapper.insertPower(power);
    }

    @Override
    public List<TbDacChannelAddupDto> addupPower(String group) {
        AddupGroupByType groupByType;
        try {
            groupByType = AddupGroupByType.valueOf(group.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("分组类型不存在");
        }
        return dacChannelMapper.addupDacChannel(EnableFlag.ENABLE.getKey(), groupByType.getStartTime(new Date()), groupByType.getGroup());
    }
}
