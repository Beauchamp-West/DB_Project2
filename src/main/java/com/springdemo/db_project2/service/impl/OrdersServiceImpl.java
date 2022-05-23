package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Orders;
import com.springdemo.db_project2.dao.OrdersDao;
import com.springdemo.db_project2.service.OrdersService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * (Orders)表服务实现类
 *
 * @author makejava
 * @since 2022-05-13 02:49:00
 */
@Service("ordersService")
public class OrdersServiceImpl implements OrdersService {
    @Resource
    private OrdersDao ordersDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Orders queryById(Integer id) {
        return this.ordersDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<Orders> queryAll() {
        return this.ordersDao.queryAll();
    }

    @Override
    public List<Orders> selectByMultiArgs(String contractNum, String enterprise, String model,
                                          String manager, String contractDate, String estimatedDeliveryDate,
                                          String lodgementDate, String salesman, String contractType) {
        return ordersDao.selectByMultiArgs(contractNum, enterprise, model, manager,
                contractDate, estimatedDeliveryDate, lodgementDate, salesman, contractType);
    }

    @Override
    public Long getOrderCount() {
        Map<String,Object> map = ordersDao.selectCnt();
        return (Long) map.get("cnt");
    }

    @Override
    public String getCountFormat() {
        return "Q8 " + getOrderCount();
    }

    /**
     * 新增数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    @Override
    public Orders insert(Orders orders) {
        this.ordersDao.insert(orders);
        return orders;
    }

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 实例对象
     */
    @Override
    public Orders update(Orders orders) {
        this.ordersDao.update(orders);
        return this.queryById(orders.getId());
    }

    @Override
    public String updateStatus() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        ordersDao.updateTypeByTime(date);

        return "All orders statuses updated!";
    }

    @Override
    @Scheduled(cron = "20/20 * * * * ? " )
    public void autoUpdate() {
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        ordersDao.updateTypeByTime(date);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.ordersDao.deleteById(id) > 0;
    }
}