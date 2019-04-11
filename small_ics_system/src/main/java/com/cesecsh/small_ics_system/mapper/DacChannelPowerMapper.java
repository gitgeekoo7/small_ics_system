package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.model.TbDacChannelPower;
import org.apache.ibatis.annotations.Insert;

public interface DacChannelPowerMapper {
    @Insert("insert into tb_dac_channel_power (id,ics_id,dac_id,dac_channel_id,dac_channel_type,`value`,create_time) " +
            "values (#{id},#{icsId},#{dacId},#{dacChannelId},#{dacChannelType},#{value},#{createTime})")
    void insertPower(TbDacChannelPower power);
}
