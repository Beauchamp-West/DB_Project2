package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.Enterprise;
import com.springdemo.db_project2.provider.EnterpriseProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Enterprise)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 02:45:21
 */
@Mapper
public interface EnterpriseDao {

    @Select("select * from enterprise where id = #{id}")
    Enterprise selectById(Integer id);

    @Select("select * from enterprise")
    List<Enterprise> selectAll();

    @Insert("insert into enterprise (name,country,city,supply_center,industry)" +
            " values(#{e.name},#{e.country},#{e.city},#{e.supplyCenter},#{e.industry})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("e") Enterprise enterprise);

    @Update("update enterprise set name=#{name} where id=#{id}")
    int update(Enterprise enterprise);

    @Delete("delete from enterprise where id=#{id}")
    int deleteById(Integer id);

    @InsertProvider(type = EnterpriseProvider.class, method = "importEnterprises")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int batchInsert(@Param("list") List<Enterprise> enterprises);

}