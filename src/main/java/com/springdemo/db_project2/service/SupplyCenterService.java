package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.SupplyCenter;

import java.util.List;

/**
 * (SupplyCenter)表服务接口
 *
 * @author makejava
 * @since 2022-05-12 17:35:30
 */
public interface SupplyCenterService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SupplyCenter selectById(Integer id);

    /**
     * 查询所有数据
     *
     * @return 对象列表
     */
    List<SupplyCenter> selectAll();

    /**
     * 新增数据
     *
     * @param name 插入名字
     * @return 插入信息
     */
    String insert(String name);

    /**
     * 修改数据
     *
     * @param name 修改后名字
     * @param id 修改id
     * @return 修改信息
     */
    String updateNameById(String name, Integer id);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    String deleteById(Integer id);

    /**
     * 导入原始数据
     *
     * @return 导入信息
     */
    String importCenters();
}