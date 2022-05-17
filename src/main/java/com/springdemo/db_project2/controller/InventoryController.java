package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.Inventory;
import com.springdemo.db_project2.service.InventoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Inventory)表控制层
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
@RestController
@RequestMapping("inventory")
public class InventoryController {
    /**
     * 服务对象
     */
    @Resource
    private InventoryService inventoryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Inventory selectOne(Integer id) {
        return this.inventoryService.queryById(id);
    }

    @GetMapping("selectAll")
    public ModelAndView selectAll() {
        List<Inventory> res = inventoryService.queryAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("inventories",res);
        mav.setViewName("select_inventory");
        return mav;
    }

}