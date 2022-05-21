package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.Contract;
import com.springdemo.db_project2.entity.Orders;
import com.springdemo.db_project2.service.OrdersService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Orders)表控制层
 *
 * @author makejava
 * @since 2022-05-13 02:49:00
 */
@RestController
@RequestMapping("orders")
public class OrdersController {
    /**
     * 服务对象
     */
    @Resource
    private OrdersService ordersService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Orders selectOne(Integer id) {
        return this.ordersService.queryById(id);
    }

    @GetMapping("selectAll")
    public ModelAndView selectAll() {
        List<Orders> res = ordersService.queryAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("orders",res);
        mav.setViewName("select_order");
        return mav;
    }

    @GetMapping("selectCnt")
    public String selectCnt() {
        Long cnt = ordersService.getOrderCount();
        return "total number of contracts: " + cnt;
    }
}