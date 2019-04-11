package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.model.TbAlarmNote;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.github.pagehelper.PageInfo;

public interface IAlarmNoteService {
	
    void saveNote(TbAlarmNote note);

    PageInfo<TbAlarmNote> listNote(QueryObject queryObject);

}
