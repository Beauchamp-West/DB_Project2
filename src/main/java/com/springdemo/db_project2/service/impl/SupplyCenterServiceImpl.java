package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.dao.SupplyCenterDao;
import com.springdemo.db_project2.service.SupplyCenterService;
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
    public List<SupplyCenter> selectAll() {
        List<SupplyCenter> supplyCenters = this.supplyCenterDao.selectAll();
        return supplyCenters;
    }

    @Override
    public String insert(String name) {
        SupplyCenter supplyCenter = new SupplyCenter();
        supplyCenter.setName(name);
        this.supplyCenterDao.insert(supplyCenter);
        return "Successfully inserted one supply center!\n";
    }

    @Override
    public String updateNameById(String name, Integer id) {
        this.supplyCenterDao.updateNameById(name, id);
        return "Successfully updated supply center " + id + " with name " + name + "!\n";
    }

    @Override
    public String deleteById(Integer id) {
        this.supplyCenterDao.deleteById(id);
        return "Successfully deleted supply center " + id + "!\n";
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
                data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                SupplyCenter supplyCenter = new SupplyCenter();
                supplyCenter.setName(data[1].replace("\"",""));
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