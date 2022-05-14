package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.StringJoiner;

/**
 * 总控制层
 */
@RestController
@RequestMapping("all")
public class GeneralController {
    /**
     * 服务对象
     */
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private SupplyCenterService supplyCenterService;
    @Resource
    private ModelService modelService;
    @Resource
    private StaffService staffService;
    @Resource
    private ContractService contractService;
    @Resource
    private OrdersService ordersService;
    @Resource
    private InventoryService inventoryService;

    /**
     * 一键导入所有原始数据
     *
     * @return 导入信息
     */
    @GetMapping("import")
    public ModelAndView importAll() {
        StringJoiner sj = new StringJoiner("");
        sj.add(supplyCenterService.importCenters());
        sj.add(enterpriseService.importEnterprises());
        sj.add(modelService.importModels());
        sj.add(staffService.importStaffs());

        ModelAndView mav = new ModelAndView();
        mav.addObject("import_info",sj.toString());
        mav.setViewName("import_all");
        return mav;
    }
}