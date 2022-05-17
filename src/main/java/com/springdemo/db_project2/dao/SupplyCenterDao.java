package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.provider.SupplyCenterProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (SupplyCenter)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-12 17:37:36
 */
@Mapper
public interface SupplyCenterDao {

    @Select("select * from supply_center where id = #{id}")
    SupplyCenter selectById(Integer id);

    @Select("select * from supply_center where name = #{name}")
    List<SupplyCenter> selectByName(String name);

    @Select("select * from supply_center")
    List<SupplyCenter> selectAll();

    @Insert("insert into supply_center (name) values(#{center.name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("center") SupplyCenter supplyCenter);

    @Update("update supply_center set name=#{name} where id=#{id}")
    int updateNameById(@Param("name")String name,@Param("id")Integer id);

    @Delete("delete from supply_center where id=#{id}")
    int deleteById(Integer id);

    /**
     * 批量新增数据
     *
     * @param supplyCenters 新增数据列表
     */
    @InsertProvider(type = SupplyCenterProvider.class, method = "importCenters")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void batchInsert(@Param("list") List<SupplyCenter> supplyCenters);

}