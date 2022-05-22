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
     * select orders by multiple parameters where each of them can be null or not
     *
     * @param contractNum contract number
     * @param enterprise enterprise name
     * @param model model
     * @param manager manager number
     * @param contractDate contract date
     * @param estimatedDeliveryDate estimated Delivery Date
     * @param lodgementDate lodgement Date
     * @param salesman salesman number
     * @param contractType contract type
     * @return list of orders
     */
    List<Orders> selectByMultiArgs(String contractNum, String enterprise, String model,
                                   String manager, String contractDate, String estimatedDeliveryDate,
                                   String lodgementDate, String salesman, String contractType);

    /**
     * total number of orders
     *
     * @return count
     */
    Long getOrderCount();

    /**
     * format the count of orders
     *
     * @return formatted count
     */
    String getCountFormat();

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
     * update order's type by the real time
     *
     * @return update info
     */
    String updateStatus();

    void autoUpdate();

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}