package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.Model;
import com.springdemo.db_project2.entity.Staff;
import com.springdemo.db_project2.provider.ModelProvider;
import com.springdemo.db_project2.provider.StaffProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Staff)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 02:50:08
 */
@Mapper
public interface StaffDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Staff queryById(Integer id);

    /**
     * 通过number查staff
     *
     * @param number 编号
     * @return 对象列表
     */
    @Select("select * from staff where number = #{number}")
    List<Staff> queryByNumber(Integer number);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Staff> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param staff 实例对象
     * @return 对象列表
     */
    List<Staff> queryAll(Staff staff);

    /**
     * 新增数据
     *
     * @param staff 实例对象
     * @return 影响行数
     */
    int insert(Staff staff);

    /**
     * 修改数据
     *
     * @param staff 实例对象
     * @return 影响行数
     */
    int update(Staff staff);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 批量新增数据
     *
     * @param staffs 新增数据列表
     */
    @InsertProvider(type = StaffProvider.class, method = "importStaffs")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void batchInsert(@Param("list") List<Staff> staffs);
}