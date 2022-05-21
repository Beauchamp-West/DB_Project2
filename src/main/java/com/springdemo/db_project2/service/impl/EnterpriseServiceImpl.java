package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Enterprise;
import com.springdemo.db_project2.dao.EnterpriseDao;
import com.springdemo.db_project2.service.EnterpriseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * (Enterprise)表服务实现类
 *
 * @author makejava
 * @since 2022-05-13 02:45:27
 */
@Service("enterpriseService")
public class EnterpriseServiceImpl implements EnterpriseService {
    @Resource
    private EnterpriseDao enterpriseDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Enterprise selectById(Integer id) {
        return this.enterpriseDao.selectById(id);
    }

    @Override
    public List<Enterprise> selectAll() { return this.enterpriseDao.selectAll(); }

    /**
     * 新增数据
     *
     * @param enterprise 实例对象
     * @return 实例对象
     */
    @Override
    public Enterprise insert(Enterprise enterprise) {
        this.enterpriseDao.insert(enterprise);
        return enterprise;
    }

    /**
     * 修改数据
     *
     * @param enterprise 实例对象
     * @return 实例对象
     */
    @Override
    public Enterprise update(Enterprise enterprise) {
        this.enterpriseDao.update(enterprise);
        return this.selectById(enterprise.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.enterpriseDao.deleteById(id) > 0;
    }

    @Override
    public String importEnterprises() {
        List<Enterprise> enterprises = new ArrayList<>();
        int cnt = 0;
        String fileName = "tables/enterprise.csv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                Enterprise enterprise = new Enterprise();
                enterprise.setName(data[1]);
                enterprise.setCountry(data[2]);
                enterprise.setCity(data[3]);
                enterprise.setSupplyCenter(data[4].replace("\"",""));
                enterprise.setIndustry(data[5]);
                enterprises.add(enterprise);
                cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.enterpriseDao.batchInsert(enterprises);
        return "Successfully imported " + cnt + " enterprises!\n";
    }
}