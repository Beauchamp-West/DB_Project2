package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.Orders;
import java.util.List;

/**
 * (Orders)表服务接口
 *
 * @author makejava
 * @since 2022-05-13 02:49:00
 */
public interface OrdersService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Orders queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<Orders> queryAll();

    /**
     * total number of orders
     *
     * @return count
     */
    Long getOrderCount();

    /**
     * 新增数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    Orders insert(Orders orders);

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    Orders update(Orders orders);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}