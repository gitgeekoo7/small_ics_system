package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.dto.TbDacChannelAddupDto;
import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbDacChannel;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.vo.TbDacChannelVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DacChannelMapper {
    @Insert("insert into tb_dac_channel (id,channel,`name`,type,enable,state,data,dac_id,check_ratio,check_resistance,address) " +
            "values (#{id},#{channel},#{name},#{type},#{enable},#{state},#{data},#{dacId},#{checkRatio},#{checkResistance},#{address})")
    void insertDacChannel(TbDacChannel dacChannel);

    @Delete("delete from tb_dac_channel where id = #{id}")
    void deleteDacChannel(@Param("id") String id);

    /**
     * 根据所属采控器删除
     */
    @Delete("delete from tb_dac_channel where dac_id = #{dacId}")
    void deleteByDacId(@Param("dacId") String dacId);

    @Select("<script>" +
            "select * from tb_dac_channel " +
            "where id = #{id} " +
            "</script>")
    TbDac getDacChannel(@Param("id") String id);

    /**
     * 根据采控器ID获取通道列表
     */
    @Select("<script>" +
            "select * from tb_dac_channel " +
            "where dac_id = #{dacId} " +
            "order by channel" +
            "</script>")
    List<TbDacChannel> listByDacId(@Param("dacId") String dacId);

    @Update("update tb_dac_channel set name = #{name},type = #{type},enable = #{enable} " +
            "where id = #{id}")
    void updateDacChannel(TbDacChannel dacChannel);

    String LIST_CONDITION = "<if test=\"name != null and name.trim != ''\"> and td.name like concat('%',#{name},'%') </if>" +
            "<if test=\"channelName != null and channelName.trim != ''\"> and tdc.name like concat('%',#{channelName},'%') </if>" +
            "<if test=\"code != null and code.trim != ''\"> and tc.code like concat('%',#{code},'%') </if>" +
            "<if test=\"address != null and address.trim != ''\"> and td.address = #{address} </if>" +
            "<if test=\"channel != null and channel.trim != ''\"> and tdc.channel = #{channel} </if>" +
            "<if test=\"state != null and state.trim != ''\"><choose> " +
            "   <when test=\"state == 2\"> and tdc.state = 0 and tdc.data is not null </when>" +
            "   <when test=\"state == 3\"> and tdc.state = 1 and tdc.data is null </when>" +
            "   <otherwise> and tdc.state = #{state} </otherwise>" +
            "</choose></if>";

    @Select("<script>" +
            "select tdc.*,td.`name` as dacName,td.address,tc.`name` as icsName,tc.state as icsState " +
            "from tb_dac_channel tdc " +
            "join tb_dac td on tdc.dac_id = td.id " +
            "join tb_ics tc on td.ics_id = tc.id " +
            "where td.del_flag = #{delFlag} and tc.del_flag = #{delFlag} " + LIST_CONDITION +
            "order by td.create_time desc, tdc.channel asc" +
            "</script>")
    List<TbDacChannelVo> listDacChannel(QueryObject queryObject);

    @Select("select * " +
            "from tb_dac_channel " +
            "where dac_id = #{dacId} and channel = #{channel}")
    TbDacChannel getDacChannelByDacIdAndChannel(@Param("dacId") String dacId, @Param("channel") String channel);

    @Results(id = "AddupResultMap", value = {
            @Result(id = true, column = "id", property = "id", jdbcType = JdbcType.VARCHAR),
            @Result(column = "{dacChannelId = id,startDate = start_date,group = group", property = "powers",
                    many = @Many(select = "com.cesecsh.small_ics_system.mapper.DacChannelPowerMapper.addupPower"))
    })
    @Select("select id,`name`,#{startDate} start_date,#{group} `group` " +
            "from tb_dac_channel " +
            "where `enable` = #{enableFlag}")
    List<TbDacChannelAddupDto> addupDacChannel(@Param("enableFlag") String enableFlag,
                                               @Param("startDate") String startDate,
                                               @Param("group") String group);
}
