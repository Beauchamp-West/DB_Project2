package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.Contract;
import com.springdemo.db_project2.service.ContractService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Contract)表控制层
 *
 * @author makejava
 * @since 2022-05-13 02:41:16
 */
@RestController
@RequestMapping("contract")
public class ContractController {
    /**
     * 服务对象
     */
    @Resource
    private ContractService contractService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Contract selectOne(Integer id) {
        return this.contractService.queryById(id);
    }

    @GetMapping("selectAll")
    public ModelAndView selectAll() {
        List<Contract> res = contractService.queryAll();
        ModelAndView mav = new ModelAndView();
        mav.addObject("contracts",res);
        mav.setViewName("select_contract");
        return mav;
    }

    @GetMapping("selectCnt")
    public String selectCnt() {
        Long cnt = contractService.getContractCount();
        return "total number of contracts: " + cnt;
    }
}