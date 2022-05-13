package com.springdemo.db_project2;

import com.springdemo.db_project2.entity.SupplyCenter;
import com.springdemo.db_project2.service.SupplyCenterService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DbProject2ApplicationTests {

    @Resource
    private SupplyCenterService supplyCenterService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;



}
