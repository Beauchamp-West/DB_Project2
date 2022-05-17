package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.service.SupplyCenterService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (SupplyCenter)表控制层
 *
 * @author makejava
 * @since 2022-05-12 17:37:39
 */
@RestController
@RequestMapping("center")
public class SupplyCenterController {
    /**
     * 服务对象
     */
    @Resource
    private SupplyCenterService supplyCenterService;

    @GetMapping("selectByInput")
    public ModelAndView selectByInput(@RequestParam(name="id") Integer id) {
        List<SupplyCenter> res = new ArrayList<>();
        res.add(supplyCenterService.selectById(id));
        ModelAndView mav = new ModelAndView();
        mav.addObject("centers",res);
        mav.setViewName("select_center");
        return mav;
    }

    @GetMapping("selectAll")
    public ModelAndView selectAll() {
        List<SupplyCenter> res = supplyCenterService.selectAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("centers",res);
        mav.setViewName("select_center");
        return mav;
    }

    @PostMapping("addByName")
    public String addByName(@RequestParam(name="name") String name) {
        return this.supplyCenterService.insert(name);
    }


    @GetMapping("deleteById")
    public String deleteById(@RequestParam(name = "id") Integer id) {
        return supplyCenterService.deleteById(id);
    }


    @GetMapping("updateName")
    public String updateName(@RequestParam(name = "name") String name, @RequestParam(name = "id") Integer id) {
        return this.supplyCenterService.updateNameById(name, id);
    }

    /**
     * 导入原始数据
     *
     * @return 导入信息
     */
    @GetMapping("import")
    public String importCenter() {return supplyCenterService.importCenters(); }
}