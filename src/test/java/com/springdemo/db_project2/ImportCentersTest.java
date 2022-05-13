package com.springdemo.db_project2;

import com.springdemo.db_project2.dao.SupplyCenterDao;
import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.service.SupplyCenterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ImportCentersTest {

    @Autowired
    private SupplyCenterDao supplyCenterDao;

    @Test
    public void testImportCenters() {
        SupplyCenter supplyCenter = new SupplyCenter();
        supplyCenter.setName("test_dao");
        supplyCenterDao.insert(supplyCenter);
        System.out.println(supplyCenterDao.selectAll());
    }
}
