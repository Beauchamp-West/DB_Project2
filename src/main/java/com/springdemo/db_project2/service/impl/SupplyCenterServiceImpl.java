package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.dao.SupplyCenterDao;
import com.springdemo.db_project2.service.SupplyCenterService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * (SupplyCenter)表服务实现类
 *
 * @author makejava
 * @since 2022-05-12 17:35:30
 */
@Service("supplyCenterService")
public class SupplyCenterServiceImpl implements SupplyCenterService {
    @Resource
    private SupplyCenterDao supplyCenterDao;

    @Override
    public SupplyCenter selectById(Integer id) {
        return this.supplyCenterDao.selectById(id);
    }

    @Override
    public String selectAll() {
        List<SupplyCenter> supplyCenters = this.supplyCenterDao.selectAll();
        StringBuilder sb = new StringBuilder();
        for (SupplyCenter supplyCenter : supplyCenters) {
            sb.append(supplyCenter.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public SupplyCenter insert(String name) {
        SupplyCenter supplyCenter = new SupplyCenter();
        supplyCenter.setName(name);
        this.supplyCenterDao.insert(supplyCenter);
        return supplyCenter;
    }

    @Override
    public String updateNameById(String name, Integer id) {
        this.supplyCenterDao.updateNameById(name, id);
        return "Successfully updated supply center " + id + " with name " + name + "!\n";
    }

    @Override
    public boolean deleteById(Integer id) {
        return this.supplyCenterDao.deleteById(id) > 0;
    }

    @Override
    public String importCenters() {
        List<SupplyCenter> supplyCenters = new ArrayList<>();
        int cnt = 0;
        String fileName = "tables/center.csv";

        try (BufferedReader infile = new BufferedReader(new FileReader(fileName))) {
            String line;
            String[] data;
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                data = line.split(",");
                SupplyCenter supplyCenter = new SupplyCenter();
                supplyCenter.setName(data[1]);
                supplyCenters.add(supplyCenter);
                cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.supplyCenterDao.batchInsert(supplyCenters);
        return "Successfully imported " + cnt + " supply centers!\n";
    }
}