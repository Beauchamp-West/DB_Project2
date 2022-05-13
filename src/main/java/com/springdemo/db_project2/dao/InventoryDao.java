package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Inventory)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
@Mapper
public interface InventoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Inventory queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Inventory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param inventory 实例对象
     * @return 对象列表
     */
    List<Inventory> queryAll(Inventory inventory);

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 影响行数
     */
    int insert(Inventory inventory);

    /**
     * 修改数据
     *
     * @param inventory 实例对象
     * @return 影响行数
     */
    int update(Inventory inventory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}