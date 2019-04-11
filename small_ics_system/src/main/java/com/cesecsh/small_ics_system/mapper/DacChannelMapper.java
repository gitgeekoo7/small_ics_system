package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbDacChannel;
import com.cesecsh.small_ics_system.query.QueryObject;
import com.cesecsh.small_ics_system.vo.TbDacChannelVo;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DacChannelMapper {
    @Insert("insert into tb_dac_channel (id,channel,`name`,type,enable,state,data,dac_id) " +
            "values (#{id},#{channel},#{name},#{type},#{enable},#{state},#{data},#{dacId})")
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
            "</script>")
    List<TbDacChannel> getByDacId(@Param("dacId") String dacId);

    @Update("update tb_dac_channel set name = #{name},type = #{type},enable = #{enable} " +
            "where id = #{id}")
    void updateDacChannel(TbDacChannel dacChannel);

     String LIST_CONDITION = "<if test=\"name != null and name.trim != ''\"> and td.name like concat('%',#{name},'%') </if>"+
    		               "<if test=\"channelName != null and channelName.trim != ''\"> and tdc.name like concat('%',#{channelName},'%') </if>"+
    		               "<if test=\"code != null and code.trim != ''\"> and tc.code like concat('%',#{code},'%') </if>"+
    		               "<if test=\"address != null and address.trim != ''\"> and td.address = #{address} </if>"+
    		               "<if test=\"channel != null and channel.trim != ''\"> and tdc.channel = #{channel} </if>"+
    		               "<if test=\"state != null and state.trim != ''\"> and tdc.state = #{state} </if>"; 

    @Select("<script>" +
            "select tdc.*,td.`name` as dacName,td.address,tc.`name` as icsName from tb_dac_channel tdc " +
    		"left join tb_dac td on tdc.dac_id = td.id "+
            "left join tb_ics tc on td.ics_id = tc.id "+
            "where td.del_flag = #{delFlag} " + LIST_CONDITION +
            "order by td.create_time desc, tdc.channel asc" +
            "</script>")
    List<TbDacChannelVo> listDacChannel(QueryObject queryObject);
}
