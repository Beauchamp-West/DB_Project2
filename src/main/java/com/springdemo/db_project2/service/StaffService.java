package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.Staff;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * (Staff)表服务接口
 *
 * @author makejava
 * @since 2022-05-13 02:50:08
 */
public interface StaffService {

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
    List<Staff> queryByNumber(Integer number);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Staff> queryAllByLimit(int offset, int limit);

    /**
     * 查询所有数据
     *
     * @return 对象列表
     */
    List<Staff> selectAll();

    /**
     * select count of staffs group by type
     *
     * @return (type,count) list
     */
    List<Map<String,Object>> getAllStaffCount();

    /**
     * format the result of getAllStaffCount
     *
     * @return formatted String
     */
    String getCountFormat();

    /**
     * 新增数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    Staff insert(Staff staff);

    /**
     * 修改数据
     *
     * @param staff 实例对象
     * @return 实例对象
     */
    Staff update(Staff staff);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 导入原始数据
     *
     * @return 导入信息
     */
    String importStaffs();

}