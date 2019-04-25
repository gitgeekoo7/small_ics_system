package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.model.TbAlarmNote;
import com.cesecsh.small_ics_system.query.QueryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AlarmNoteMapper {

    @Insert("insert into tb_alarm_note (id,ics_id,ics_name,input,threshold,output,dac_id,dac_name,dac_channel_id,dac_channel_name,linkage_action,alarm_time,alarm_setting_id,alarm_setting_name) "
            + "values (#{id},#{icsId},#{icsName},#{input},#{threshold},#{output},#{dacId},#{dacName},#{dacChannelId},#{dacChannelName},#{linkageAction},#{alarmTime},#{alarmSettingId},#{alarmSettingName})")
    void insertNote(TbAlarmNote note);

    String LIST_CONDITION = "<where>"
            + "<if test=\"dacChannelName != null and dacChannelName.trim != ''\"> and dac_channel_name like concat('%',#{dacChannelName},'%') </if>"
            + "<if test=\"icsName != null and icsName.trim != ''\"> and ics_name like concat('%',#{icsName},'%') </if>"
            + "</where>";

    @Select("<script>" +
            "select * " +
            "from tb_alarm_note " + LIST_CONDITION +
            "order by alarm_time desc" +
            "</script>")
    List<TbAlarmNote> listNote(QueryObject queryObject);
}
