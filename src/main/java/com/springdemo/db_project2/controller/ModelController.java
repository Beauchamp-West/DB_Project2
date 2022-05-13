package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.Model;
import com.springdemo.db_project2.service.ModelService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Model)表控制层
 *
 * @author makejava
 * @since 2022-05-13 02:47:40
 */
@RestController
@RequestMapping("model")
public class ModelController {
    /**
     * 服务对象
     */
    @Resource
    private ModelService modelService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("select/{id}")
    public Model select(@Param("id") Integer id) {
        return this.modelService.selectById(id);
    }

    /**
     * 导入原始数据
     *
     * @return 导入信息
     */
    @GetMapping("import")
    public String importModel() {return modelService.importModels(); }

}