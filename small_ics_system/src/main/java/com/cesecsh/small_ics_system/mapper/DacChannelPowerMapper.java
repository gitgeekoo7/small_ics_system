package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.dto.AddupDto;
import com.cesecsh.small_ics_system.model.TbDacChannelPower;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface DacChannelPowerMapper {
    @Insert("insert into tb_dac_channel_power (id,ics_id,dac_id,dac_channel_id,dac_channel_type,`value`,create_time) " +
            "values (#{id},#{icsId},#{dacId},#{dacChannelId},#{dacChannelType},#{value},#{createTime})")
    void insertPower(TbDacChannelPower power);

    @Select("select ${group} group_name,sum(`value`) group_value " +
            "from tb_dac_channel_power " +
            "where dac_channel_id = #{dacChannelId} and  create_time >= #{startDate} " +
            "group by ${group}")
    AddupDto addupPower(@Param("dacChannelId") String dacChannelId,
                        @Param("startDate") String startDate,
                        @Param("group") String group);
}
