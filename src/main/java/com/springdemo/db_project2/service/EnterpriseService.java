package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.Enterprise;
import java.util.List;

/**
 * (Enterprise)表服务接口
 *
 * @author makejava
 * @since 2022-05-13 02:45:25
 */
public interface EnterpriseService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Enterprise selectById(Integer id);

    /**
     * 查询所有数据
     *
     * @return 对象列表
     */
    List<Enterprise> selectAll();

    /**
     * 新增数据
     *
     * @param enterprise 实例对象
     * @return 实例对象
     */
    Enterprise insert(Enterprise enterprise);

    /**
     * 修改数据
     *
     * @param enterprise 实例对象
     * @return 实例对象
     */
    Enterprise update(Enterprise enterprise);

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
    String importEnterprises();
}