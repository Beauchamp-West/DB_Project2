package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.Enterprise;
import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.service.EnterpriseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Enterprise)表控制层
 *
 * @author makejava
 * @since 2022-05-13 02:45:30
 */
@RestController
@RequestMapping("enterprise")
public class EnterpriseController {
    /**
     * 服务对象
     */
    @Resource
    private EnterpriseService enterpriseService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("select/{id}")
    public Enterprise select(@PathVariable("id") Integer id) {
        return this.enterpriseService.selectById(id);
    }

    @GetMapping("selectAll")
    public ModelAndView selectAll() {
        List<Enterprise> res = enterpriseService.selectAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("enterprises",res);
        mav.setViewName("select_enterprise");
        return mav;
    }

    /**
     * 导入原始数据
     *
     * @return 导入信息
     */
    @GetMapping("import")
    public String importCenter() {return enterpriseService.importEnterprises(); }
}