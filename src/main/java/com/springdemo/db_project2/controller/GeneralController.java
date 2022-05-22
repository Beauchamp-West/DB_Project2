package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.service.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
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
    @Resource
    private GeneralService generalService;

    /**
     * 一键导入所有原始数据
     *
     * @return 导入信息
     */
    @GetMapping("importOrigin")
    public ModelAndView importOrigin() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(supplyCenterService.importCenters());
        sj.add(enterpriseService.importEnterprises());
        sj.add(modelService.importModels());
        sj.add(staffService.importStaffs());

        ModelAndView mav = new ModelAndView();
        mav.addObject("import_info",sj.toString());
        mav.setViewName("import_all");
        return mav;
    }

    /**
     * 一键导入所有数据
     *
     * @return 导入信息
     */
    @GetMapping("import")
    public ModelAndView importAll() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(supplyCenterService.importCenters());
        sj.add(enterpriseService.importEnterprises());
        sj.add(modelService.importModels());
        sj.add(staffService.importStaffs());
        sj.add(generalService.stockInAll());
        sj.add(generalService.placeAll());
        sj.add(generalService.updateAll());
        sj.add(generalService.deleteAll());

        ModelAndView mav = new ModelAndView();
        mav.addObject("import_info",sj.toString());
        mav.setViewName("import_all");
        return mav;
    }

    /**
     * 一键导入所有测试数据
     *
     * @return 导入信息
     */
    @GetMapping("importTest")
    public ModelAndView importTest() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(generalService.stockInAll());
        sj.add(generalService.placeAll());
        sj.add(generalService.updateAll());
        sj.add(generalService.deleteAll());

        ModelAndView mav = new ModelAndView();
        mav.addObject("import_info",sj.toString());
        mav.setViewName("import_all");
        return mav;
    }

    /**
     * import test data for auto update
     *
     * @return 导入信息
     */
    @GetMapping("testUpdate")
    public ModelAndView importUpdateTest() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(generalService.importUpdateTest());

        ModelAndView mav = new ModelAndView();
        mav.addObject("import_info",sj.toString());
        mav.setViewName("import_all");
        return mav;
    }

    @GetMapping("stockIn")
    public String stockIn(@RequestParam(name = "center") String center, @RequestParam(name = "model") String model,
                          @RequestParam(name = "staff") Integer staffNumber, @RequestParam(name = "date") String date,
                          @RequestParam(name = "price") Integer price, @RequestParam(name = "q") Integer quantity) {
        return generalService.stockIn(center, model, staffNumber, date, price, quantity);
    }

    @GetMapping("placeOrder")
    public String placeOrder(@RequestParam("c_num") String contract_num, @RequestParam("enterprise") String enterprise,
                             @RequestParam("model") String product_model, @RequestParam("quantity") Integer sold,
                             @RequestParam("manager") Integer contract_manager, @RequestParam("c_date") String contract_date,
                             @RequestParam("e_date") String estimated_delivery_date, @RequestParam("l_date") String lodgement_date,
                             @RequestParam("s_num") Integer salesman_num, @RequestParam("type") String contract_type) {
        return generalService.placeOrder(contract_num,enterprise,product_model,sold, contract_manager,
                contract_date,estimated_delivery_date, lodgement_date,salesman_num,
                contract_type);
    }

    @GetMapping("updateOrder")
    public String updateOrder(@RequestParam("c_num") String contract_num, @RequestParam("model") String product_model,
                              @RequestParam("s_num") Integer salesman_num, @RequestParam("quantity") Integer quantity,
                              @RequestParam("e_date") String estimated_delivery_date, @RequestParam("l_date") String lodgement_date) {
        return generalService.updateOrder(contract_num, product_model, salesman_num, quantity,
                estimated_delivery_date, lodgement_date);
    }

    @GetMapping("deleteOrder")
    public String deleteOrder(@RequestParam("c_num") String contract_num, @RequestParam("s_num") Integer salesman_num,
                              @RequestParam("seq") Integer seq) {
        return generalService.deleteOrder(contract_num, salesman_num, seq);
    }

    @GetMapping("output")
    public ModelAndView output() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(staffService.getCountFormat());
        sj.add(contractService.getCountFormat());
        sj.add(ordersService.getCountFormat());
        sj.add(inventoryService.getNeverSoldCountFormat());
        sj.add(inventoryService.getFavoriteFormat());
        sj.add(inventoryService.getAvgFormat());

        ModelAndView mav = new ModelAndView();
        mav.addObject("import_info",sj.toString());
        mav.setViewName("import_all");
        return mav;
    }
}
