package com.cesecsh.small_ics_system.service.impl;

import com.cesecsh.small_ics_system.mapper.AlarmNoteMapper;
import com.cesecsh.small_ics_system.model.TbAlarmNote;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.service.IAlarmNoteService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlarmNoteServiceImpl implements IAlarmNoteService {
	
    @Autowired
    private AlarmNoteMapper alarmNoteMapper;

	@Override
	public void saveNote(TbAlarmNote note) {
		note.setId(UUID.randomUUID().toString().replace("-", ""));
		alarmNoteMapper.insertNote(note);
		
	}

	@Override
	@Transactional(readOnly = true)
	public PageInfo<TbAlarmNote> listNote(QueryObject queryObject) {
		List<TbAlarmNote> noteList =  alarmNoteMapper.listNote(queryObject);
		return new PageInfo<>(noteList);
	}

}
