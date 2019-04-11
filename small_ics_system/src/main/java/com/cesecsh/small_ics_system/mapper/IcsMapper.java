package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.model.TbIcs;
import com.cesecsh.small_ics_system.query.QueryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IcsMapper {
    @Insert("insert into tb_ics (id,`name`,serial,code,ip,server_ip,submask,gateway,state,version,remark,del_flag,create_time,create_user_id,update_time,update_user_id) " +
            "values (#{id},#{name},#{serial},#{code},#{ip},#{serverIp},#{submask},#{gateway},#{state},#{version},#{remark},#{delFlag},#{createTime},#{createUserId},#{updateTime},#{updateUserId})")
    void insertIcs(TbIcs ics);

    @Update("update tb_ics " +
            "set del_flag = #{delFlag},update_time = #{updateTime} " +
            "where id = #{id}")
    void deleteIcs(TbIcs ics);

    String GET_CONDITION = "<if test=\"delFlag != null and delFlag.trim != ''\"> and del_flag = #{delFlag} </if>";

    @Select("<script>" +
            "select * " +
            "from tb_ics " +
            "where id = #{id} " + GET_CONDITION +
            "</script>")
    TbIcs getIcs(@Param("id") String id, @Param("delFlag") String delFlag);

    @Update("update tb_ics " +
            "set name = #{name},code = #{code},remark = #{remark},ip = #{ip},server_ip = #{serverIp},gateway = #{gateway},submask = #{submask},update_time = #{updateTime} " +
            "where id = #{id}")
    void updateIcs(TbIcs ics);

    String LIST_CONDITION = "<if test=\"name != null and name.trim != ''\"> and name like concat('%',#{name},'%') </if>" +
            "<if test=\"ip != null and ip.trim != ''\"> and ip like concat('%',#{ip},'%') </if>";

    @Select("<script>" +
            "select * " +
            "from tb_ics " +
            "where del_flag = #{delFlag} " + LIST_CONDITION +
            "order by create_time desc" +
            "</script>")
    List<TbIcs> listIcs(QueryObject queryObject);

    @Select("select * " +
            "from tb_ics " +
            "where serial = #{serial}")
    TbIcs getIcsBySerial(@Param("serial") String serial);
}
