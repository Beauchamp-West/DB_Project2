package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.Inventory;
import com.springdemo.db_project2.service.InventoryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @GetMapping("neverSoldCnt")
    public String neverSoldCnt() {
        Long cnt = inventoryService.getNeverSoldProductCount();
        return "total number of never sold product models: " + cnt;
    }

    @GetMapping("favorite")
    public ModelAndView favorite() {
        List<Map<String,Object>> res = inventoryService.getFavoriteProductModel();
        ModelAndView mav = new ModelAndView();
        mav.addObject("favorites",res);
        mav.setViewName("inventory_favorite");
        return mav;
    }

    @GetMapping("avg")
    public ModelAndView avg() {
        List<Map<String,Object>> res = inventoryService.getAvgStockByCenter();
        ModelAndView mav = new ModelAndView();
        mav.addObject("averages",res);
        mav.setViewName("inventory_avg");
        return mav;
    }

    @GetMapping("product")
    public ModelAndView product(@RequestParam("num") String productNum) {
        List<Map<String,Object>> res = inventoryService.getProductByNumber(productNum);
        ModelAndView mav = new ModelAndView();
        mav.addObject("products",res);
        mav.setViewName("inventory_product");
        return mav;
    }

}