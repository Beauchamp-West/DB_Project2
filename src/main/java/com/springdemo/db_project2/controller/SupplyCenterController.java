package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.service.SupplyCenterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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


    @GetMapping("select/{id}")
    public SupplyCenter select(@PathVariable("id") Integer id) {
        return this.supplyCenterService.selectById(id);
    }

    @GetMapping("selectAll")
    public String selectAll() {
        return this.supplyCenterService.selectAll();
    }

    @PostMapping("add/{name}")
    public SupplyCenter add(@PathVariable("name") String name) {
        return this.supplyCenterService.insert(name);
    }

    @PutMapping("update/{id}/{name}")
    public String update(@PathVariable("name") String name, @PathVariable("id") Integer id) {
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