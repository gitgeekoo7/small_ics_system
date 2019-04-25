package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.dto.TbDacDto;
import com.cesecsh.small_ics_system.mapper.DacChannelMapper;
import com.cesecsh.small_ics_system.mapper.DacMapper;
import com.cesecsh.small_ics_system.mapper.IcsMapper;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbDacChannel;
import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.service.IDacService;
import com.cesecsh.small_ics_system.util.DelFlag;
import com.cesecsh.small_ics_system.util.EnableFlag;
import com.cesecsh.small_ics_system.util.RunningState;
import com.cesecsh.small_ics_system.util.WorkingState;
import com.cesecsh.small_ics_system.vo.TbDacChannelVo;
import com.cesecsh.small_ics_system.vo.TbDacVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DacServiceImpl implements IDacService {

    @Autowired
    private DacMapper dacMapper;
    @Autowired
    private DacChannelMapper dacChannelMapper;
    @Autowired
    private IcsMapper icsMapper;

    @Override
    public void saveDac(TbDac dac) {
        dac.setId(UUID.randomUUID().toString().replace("-", ""));
        dac.setDelFlag(DelFlag.UN_DELETED.getKey());
        dac.setCreateTime(new Date());
        dac.setUpdateTime(new Date());
        dacMapper.insertDac(dac);

        for (int i = 0; i < 4; i++) {
            TbDacChannel channel = new TbDacChannel();
            channel.setId(UUID.randomUUID().toString().replace("-", ""));

            channel.setName("线路" + (i + 1));
            channel.setChannel("0" + (i + 1));

            channel.setCheckRatio("02");
            channel.setCheckResistance("0A");

            channel.setEnable(EnableFlag.ENABLE.getKey());
            channel.setState(RunningState.CLOSE.getKey());
            channel.setDacId(dac.getId());

            channel.setAddress("0B01FF" + channel.getChannel() + "01" + dac.getAddress());
            dacChannelMapper.insertDacChannel(channel);
        }

//        List<TbDacChannel> channelList = dac.getChannelList();
//        for (TbDacChannel channel : channelList) {
//            channel.setId(UUID.randomUUID().toString().replace("-", ""));
//            channel.setDacId(dac.getId());
//            dacChannelMapper.insertDacChannel(channel);
//        }

    }

    @Override
    public void deleteDac(String id) {
        TbDac dac = new TbDac();
        dac.setId(id);
        dac.setDelFlag(DelFlag.DELETED.getKey());
        dac.setUpdateTime(new Date());
        dacMapper.deleteDac(dac);

    }

    @Override
    public void updateDac(TbDacVo vo) throws Exception {
        TbDac dac = dacMapper.getDac(vo.getId(), null);
        BeanUtils.copyProperties(dac, vo);
        dac.setUpdateTime(new Date());
        dacMapper.updateDac(dac);
        //采控器通道编辑
        List<TbDacChannel> channelList = dac.getChannelList();
        for (TbDacChannel channel : channelList) {
            dacChannelMapper.updateDacChannel(channel);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<TbDacChannelVo> listDac(QueryObject queryObject) {
        queryObject.setDelFlag(DelFlag.UN_DELETED.getKey());
        List<TbDacChannelVo> list = dacChannelMapper.listDacChannel(queryObject);
        for (TbDacChannelVo vo : list) {
            String state = vo.getState();
            if (StringUtils.isBlank(vo.getData()) && RunningState.OPEN.getKey().equals(state)) {
                state = "设置开";
            } else if (StringUtils.isNotBlank(vo.getData()) && RunningState.CLOSE.getKey().equals(state)) {
                state = "设置关";
            } else {
                state = RunningState.getValueByKey(state);
            }
            vo.setState(state);
            state = WorkingState.getValueByKey(vo.getIcsState());
            vo.setIcsState(state);
        }
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(readOnly = true)
    public TbDacDto getDac(String id) throws Exception {
        TbDacDto dto = new TbDacDto();
        TbDac dac = dacMapper.getDac(id, DelFlag.UN_DELETED.getKey());
        List<TbDacChannel> channelList = dacChannelMapper.listByDacId(id);
        BeanUtils.copyProperties(dto, dac);
        dto.setChannelList(channelList);
        TbIcs ics = icsMapper.getIcs(dac.getIcsId(), null);
        dto.setIcsName(ics.getName());
        dto.setIcsIp(ics.getIp());
        return dto;
    }

    @Override
    public List<TbDac> listDacByIcsId(String icsId) {
        return dacMapper.listDacByIcsId(icsId, DelFlag.UN_DELETED.getKey());
    }

}
