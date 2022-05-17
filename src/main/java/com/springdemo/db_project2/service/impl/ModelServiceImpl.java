package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Enterprise;
import com.springdemo.db_project2.entity.Model;
import com.springdemo.db_project2.dao.ModelDao;
import com.springdemo.db_project2.service.ModelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    public Model selectById(Integer id) {
        return this.modelDao.selectById(id);
    }

    @Override
    public List<Model> queryByModel(String m) {
        return modelDao.queryByModel(m);
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
        return this.selectById(model.getId());
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

    @Override
    public String importModels() {
        List<Model> modelList = new ArrayList<>();
        int cnt = 0;
        String fileName = "tables/model.csv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                data = line.split(",");
                Model model = new Model();
                model.setNumber(data[1]);
                model.setModel(data[2]);
                model.setName(data[3]);
                model.setUnitPrice(Integer.parseInt(data[4]));
                modelList.add(model);
                cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.modelDao.batchInsert(modelList);
        return "Successfully imported " + cnt + " models!\n";
    }
}