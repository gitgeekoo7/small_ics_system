package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.model.TbDac;
import com.cesecsh.small_ics_system.query.QueryObject;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DacMapper {
    @Insert("insert into tb_dac (id,order_number,address,`name`,detail,ics_id,remark,del_flag,create_time,create_user_id,update_time,update_user_id) " +
            "values (#{id},#{orderNumber},#{address},#{name},#{detail},#{icsId},#{remark},#{delFlag},#{createTime},#{createUserId},#{updateTime},#{updateUserId})")
    void insertDac(TbDac dac);

    @Update("update tb_dac " +
            "set del_flag = #{delFlag},update_time = #{updateTime} " +
            "where id = #{id}")
    void deleteDac(TbDac dac);

    String GET_CONDITION = "<if test=\"delFlag != null and delFlag.trim != ''\"> and del_flag = #{delFlag} </if>";

    @Select("<script>" +
            "select * " +
            "from tb_dac " +
            "where id = #{id} " + GET_CONDITION +
            "</script>")
    TbDac getDac(@Param("id") String id, @Param("delFlag") String delFlag);

    @Update("update tb_dac " +
            "set name = #{name},detail = #{detail},remark = #{remark},update_time = #{updateTime} " +
            "where id = #{id}")
    void updateDac(TbDac dac);

    String LIST_CONDITION = "<if test=\"name != null and name.trim != ''\"> and name like concat('%',#{name},'%') </if>";

    @Select("<script>" +
            "select * " +
            "from tb_dac " +
            "where del_flag = #{delFlag} " + LIST_CONDITION +
            "order by create_time desc" +
            "</script>")
    List<TbDac> listDac(QueryObject queryObject);

    @Select("select * " +
            "from tb_dac " +
            "where ics_id = #{icsId} and address = #{address}")
    TbDac getDacByIcsIdAndAddress(@Param("icsId") String icsId, @Param("address") String address);
}
