package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.dto.TbAlarmSettingDto;
import com.cesecsh.small_ics_system.model.TbAlarmSetting;
import com.cesecsh.small_ics_system.query.QueryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AlarmSettingMapper {
    @Insert("insert into tb_alarm_setting (id,ics_id,input_port,`trigger`,output_port,alarm_action,dac_id,dac_channel_id,linkage_action,del_flag,create_time,create_user_id,update_time,update_user_id) " +
            "values (#{id},#{icsId},#{inputPort},#{trigger},#{outputPort},#{alarmAction},#{dacId},#{dacChannelId},#{linkageAction},#{delFlag},#{createTime},#{createUserId},#{updateTime},#{updateUserId})")
    void insertSetting(TbAlarmSetting setting);

    @Update("update tb_alarm_setting " +
            "set del_flag = #{delFlag},update_time = #{updateTime} " +
            "where id = #{id}")
    void deleteSetting(TbAlarmSetting setting);

    @Update("update tb_alarm_setting " +
            "set ics_id = #{icsId},input_port = #{inputPort},`trigger` = #{trigger},output_port = #{outputPort},alarm_action = #{alarmAction},dac_id = #{dacId},dac_channel_id = #{dacChannelId},linkage_action = #{linkageAction} " +
            "where id = #{id}")
    void updateSetting(TbAlarmSetting setting);

    String LIST_CONDITION = "<if test=\"icsName != null and icsName.trim != ''\"> and i.name like concat('%',#{icsName},'%') </if>" +
            "<if test=\"dacName != null and dacName.trim != ''\"> and d.name like concat('%',#{dacName},'%') </if>" +
            "<if test=\"dacChannelName != null and dacChannelName.trim != ''\"> and dc.name like concat('%',#{dacChannelName},'%') </if>" +
            "<if test=\"inputPort != null and inputPort.trim != ''\"> and als.inputPort = #{inputPort} </if>" +
            "<if test=\"outputPort != null and outputPort.trim != ''\"> and als.outputPort = #{outputPort} </if>";

    @Select("<script>" +
            "select als.*,i.name ics_name,d.name dac_name,dc.name dac_channel_name " +
            "from tb_alarm_setting als " +
            "left join tb_ics i on i.id = als.ics_id " +
            "left join tb_dac d on d.id = als.dac_id " +
            "left join tb_dac_channel dc on dc.id = als.dac_channel_id " +
            "where als.del_flag = #{delFlag} " + LIST_CONDITION +
            "order by als.create_time desc" +
            "</script>")
    List<TbAlarmSettingDto> listSetting(QueryObject queryObject);

    @Select("select * " +
            "from tb_alarm_setting " +
            "where id = #{id} and del_flag = #{delFlag}")
    TbAlarmSetting getSetting(@Param("id") String id, @Param("delFlag") String delFlag);
}
