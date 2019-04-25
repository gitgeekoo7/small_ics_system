package com.cesecsh.small_ics_system.web.controller;

import com.cesecsh.small_ics_system.model.TbAlarmNote;
import com.cesecsh.small_ics_system.query.AlarmNoteQueryObject;
import com.cesecsh.small_ics_system.service.IAlarmNoteService;
import com.cesecsh.small_ics_system.util.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报警记录controller
 */
@RestController
@RequestMapping("/alarm/note")
public class AlarmNoteController {
    @Autowired
    private IAlarmNoteService alarmNoteService;

    @RequestMapping("/save")
    public Result saveNote(@RequestBody TbAlarmNote note) {
        alarmNoteService.saveNote(note);
        return Result.success();
    }

    @RequestMapping("/list")
    public Result listSetting(@RequestBody AlarmNoteQueryObject queryObject) {
        PageInfo<TbAlarmNote> pageInfo = alarmNoteService.listNote(queryObject);
        return Result.success(pageInfo);
    }
}
