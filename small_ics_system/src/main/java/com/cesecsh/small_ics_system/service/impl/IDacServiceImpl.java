package com.cesecsh.small_ics_system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import com.cesecsh.small_ics_system.vo.TbIcsVo;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDac(TbIcsVo vo) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public PageInfo<TbDac> listDac(QueryObject queryObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TbDac getDac(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
