package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.model.TbDacChannel;
import com.cesecsh.small_ics_system.query.QueryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DacChannelMapper {
    @Insert("insert into tb_dac_channel (id,channel,`name`,type,enable,state,data,dac_id) " +
            "values (#{id},#{channel},#{name},#{type},#{enable},#{state},#{data},#{dacId})")
    void insertDacChannel(TbDacChannel dacChannel);

   /* @Update("update tb_dac set del_flag = #{delFlag},update_time = #{updateTime} " +
            "where id = #{id}")
    void deleteDac(TbDac dac);

    String GET_CONDITION = "<if test=\"delFlag != null and delFlag.trim != ''\"> and del_flag = #{delFlag} </if>";

    @Select("<script>" +
            "select * from tb_dac " +
            "where id = #{id} " + GET_CONDITION +
            "</script>")
    TbDac getDac(@Param("id") String id, @Param("delFlag") String delFlag);

    @Update("update tb_dac set name = #{name},code = #{code},remark = #{remark},ip = #{ip},server_ip = #{serverIp},gateway = #{gateway},submask = #{submask},update_time = #{updateTime} " +
            "where id = #{id}")
    void updateDac(TbDac dac);

    String LIST_CONDITION = "<if test=\"name != null and name.trim != ''\"> and name like concat('%',#{name},'%') </if>" +
            "<if test=\"ip != null and ip.trim != ''\"> and ip like concat('%',#{ip},'%') </if>";

    @Select("<script>" +
            "select * from tb_dac " +
            "where del_flag = #{delFlag} " + LIST_CONDITION +
            "order by create_time desc" +
            "</script>")
    List<TbDac> listDac(QueryObject queryObject);*/
}
