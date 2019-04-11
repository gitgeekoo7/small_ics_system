package com.cesecsh.small_ics_system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesecsh.small_ics_system.mapper.DacChannelMapper;
import com.cesecsh.small_ics_system.mapper.DacMapper;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbDacChannel;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.service.IDacService;
import com.cesecsh.small_ics_system.util.DelFlag;
import com.cesecsh.small_ics_system.vo.TbDacChannelVo;
import com.cesecsh.small_ics_system.vo.TbDacVo;
import com.github.pagehelper.PageInfo;

@Service
@Transactional
public class IDacServiceImpl implements IDacService {
	
	 @Autowired
	 private DacMapper dacMapper;
	 @Autowired
	 private DacChannelMapper dacChannelMapper;

	@Override
	public void saveDac(TbDac dac) {
		dac.setId(UUID.randomUUID().toString().replace("-", ""));
		dac.setDelFlag(DelFlag.UN_DELETED.getKey());
		dac.setCreateTime(new Date());
		dac.setUpdateTime(new Date());
		dacMapper.insertDac(dac);
		List<TbDacChannel> channelList = dac.getChannelList();
		for(TbDacChannel channel : channelList) {
			channel.setId(UUID.randomUUID().toString().replace("-", ""));
			channel.setDacId(dac.getId());
			dacChannelMapper.insertDacChannel(channel);
		}

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
		//删除原有采控器通道数据，重新添加
		dacChannelMapper.deleteByDacId(dac.getId());
		List<TbDacChannel> channelList = dac.getChannelList();
		for(TbDacChannel channel : channelList) {
			channel.setId(UUID.randomUUID().toString().replace("-", ""));
			channel.setDacId(vo.getId());
			dacChannelMapper.insertDacChannel(channel);
		}
		
	}

	@Override
    @Transactional(readOnly = true)
	public PageInfo<TbDacChannelVo> listDac(QueryObject queryObject) {
		queryObject.setDelFlag(DelFlag.UN_DELETED.getKey());
		List<TbDacChannelVo> list = dacChannelMapper.listDacChannel(queryObject);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional(readOnly = true)
	public TbDac getDac(String id) {
		TbDac dac = dacMapper.getDac(id, DelFlag.UN_DELETED.getKey());
		List<TbDacChannel> channelList = dacChannelMapper.getByDacId(id);
		dac.setChannelList(channelList);
		return dac;
	}

}
