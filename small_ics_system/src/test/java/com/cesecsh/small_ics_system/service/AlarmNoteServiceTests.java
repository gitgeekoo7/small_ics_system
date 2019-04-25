package com.cesecsh.small_ics_system.service;

import com.cesecsh.small_ics_system.model.TbAlarmNote;
import com.cesecsh.small_ics_system.query.AlarmNoteQueryObject;
import com.cesecsh.small_ics_system.util.RunningState;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmNoteServiceTests {
    @Autowired
    private IAlarmNoteService alarmNoteService;

    @Test
    public void textSave() {
        TbAlarmNote note = new TbAlarmNote();
        note.setIcsId("3fdb438e5ee64a0db7449733e299b869");
        note.setInput("01");
        note.setThreshold(11);
        note.setOutput("01");
        note.setDacId("7006520f509d4b67924eefef43f01297");
        note.setDacChannelId("689d1dca1e45401fa59e2e21dcce4b94");
        note.setLinkageAction(RunningState.OPEN.getKey());
        alarmNoteService.saveNote(note);
    }

    @Test
    public void testList() throws Exception {
        AlarmNoteQueryObject queryObject = new AlarmNoteQueryObject();
        queryObject.setPageSize(10);
        queryObject.setPageNum(1);
        PageInfo<TbAlarmNote> pageInfo = alarmNoteService.listNote(queryObject);
        System.out.println(new ObjectMapper().writeValueAsString(pageInfo));
    }

}
