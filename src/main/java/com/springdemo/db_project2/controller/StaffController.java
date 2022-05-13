package com.springdemo.db_project2.controller;

import com.springdemo.db_project2.entity.Staff;
import com.springdemo.db_project2.service.StaffService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Staff)表控制层
 *
 * @author makejava
 * @since 2022-05-13 02:50:08
 */
@RestController
@RequestMapping("staff")
public class StaffController {
    /**
     * 服务对象
     */
    @Resource
    private StaffService staffService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("select/{id}")
    public Staff select(@Param("id") Integer id) {
        return this.staffService.queryById(id);
    }

    /**
     * 导入原始数据
     *
     * @return 导入信息
     */
    @GetMapping("import")
    public String importStaff() {return staffService.importStaffs(); }

}