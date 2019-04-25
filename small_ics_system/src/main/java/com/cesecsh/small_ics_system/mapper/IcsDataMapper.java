package com.cesecsh.small_ics_system.mapper;

import com.cesecsh.small_ics_system.model.TbIcsData;
import com.cesecsh.small_ics_system.vo.TbIcsDataVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IcsDataMapper {
    @Insert("insert into tb_ics_data (id,ics_id,data,`name`,unit,create_time,create_user_id,update_time,update_user_id) " +
            "values (#{id},#{icsId},#{data},#{name},#{unit},#{createTime},#{createUserId},#{updateTime},#{updateUserId})")
    void insert(TbIcsData data);

    @Update("update tb_ics_data " +
            "set `name` = #{name},unit = #{unit},update_time = #{updateTime} " +
            "where id = #{id}")
    void update(TbIcsData data);

    @Select("select * " +
            "from tb_ics_data " +
            "where ics_id = #{icsId}")
    TbIcsData getDataByIcsId(String icsId);

    @Insert("insert into tb_ics_data_check (id,ics_data_id,recive_value,check_value) " +
            "values (#{id},#{ics_data_id},#{recive_value},#{check_value})")
    void insertDataCheck(TbIcsDataVo data);

    @Select("select * " +
            "from tb_ics_data_check " +
            "where ics_data_id = #{icsDataId}")
    List<TbIcsDataVo> listDataCheckByIcsDataId(@Param("icsDataId") String icsDataId);

    @Delete("delete from tb_ics_data_check " +
            "where id = #{id}")
    void deleteDataCheck(@Param("id") String id);

    @Select("select check_value " +
            "from tb_ics_data_check " +
            "where ics_data_id = #{icsDataId} and recive_value = #{reciveValue}")
    String getCheckValueByIcsDataIdAndReciveValue(@Param("icsDataId") String icsDataId, @Param("reciveValue") String reciveValue);

    @Update("update tb_ics_data " +
            "set data = #{data} " +
            "where id = #{id}")
    void saveDataValue(@Param("id") String id, @Param("data") String data);
}
