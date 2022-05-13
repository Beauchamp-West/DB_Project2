package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Model;
import com.springdemo.db_project2.dao.ModelDao;
import com.springdemo.db_project2.service.ModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Model)表服务实现类
 *
 * @author makejava
 * @since 2022-05-13 02:47:40
 */
@Service("modelService")
public class ModelServiceImpl implements ModelService {
    @Resource
    private ModelDao modelDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Model queryById(Integer id) {
        return this.modelDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Model> queryAllByLimit(int offset, int limit) {
        return this.modelDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    @Override
    public Model insert(Model model) {
        this.modelDao.insert(model);
        return model;
    }

    /**
     * 修改数据
     *
     * @param model 实例对象
     * @return 实例对象
     */
    @Override
    public Model update(Model model) {
        this.modelDao.update(model);
        return this.queryById(model.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.modelDao.deleteById(id) > 0;
    }
}