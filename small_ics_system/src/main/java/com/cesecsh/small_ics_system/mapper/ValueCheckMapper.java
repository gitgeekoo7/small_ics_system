package com.cesecsh.small_ics_system.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ValueCheckMapper {
    @Select("select check_value " +
            "from tb_value_check " +
            "where check_ratio = #{ratio} and check_resistance = #{resistance} and check_power = #{power}")
    String getValueByRatioAndResistanceAndPower(@Param("ratio") String ratio,
                                                @Param("resistance") String resistance,
                                                @Param("power") String power);
}
