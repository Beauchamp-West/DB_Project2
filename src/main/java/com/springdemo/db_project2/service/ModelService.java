package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.Model;
import java.util.List;

/**
 * (Model)表服务接口
 *
 * @author makejava
 * @since 2022-05-13 02:47:40
 */
public interface ModelService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Model selectById(Integer id);

    /**
     * 新增数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    Model insert(Model model);

    /**
     * 修改数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    Model update(Model model);

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
    String importModels();
}