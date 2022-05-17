package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.Inventory;
import java.util.List;

/**
 * (Inventory)表服务接口
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
public interface InventoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Inventory queryById(Integer id);

    /**
     * 查询全部数据
     *
     * @return 对象列表
     */
    List<Inventory> queryAll();

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    String insert(Inventory inventory);

    /**
     * 修改数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    Inventory update(Inventory inventory);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}