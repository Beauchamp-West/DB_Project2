package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Contract;
import com.springdemo.db_project2.dao.ContractDao;
import com.springdemo.db_project2.service.ContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * (Contract)表服务实现类
 *
 * @author makejava
 * @since 2022-05-13 02:41:16
 */
@Service("contractService")
public class ContractServiceImpl implements ContractService {
    @Resource
    private ContractDao contractDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Contract queryById(Integer id) {
        return this.contractDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    @Override
    public List<Contract> queryAll() {
        return this.contractDao.queryAll();
    }

    @Override
    public Long getContractCount() {
        Map<String,Object> map = contractDao.selectCnt();
        return (Long) map.get("cnt");
    }

    @Override
    public String getCountFormat() {
        return "Q7 " + getContractCount();
    }

    @Override
    public String getContractInfo(String contractNum) {
        List<Map<String,Object>> contractMapList = contractDao.selectContractInfo(contractNum);
        StringJoiner sj = new StringJoiner("\n");
        Map<String,Object> contractMap = contractMapList.get(0);
        String c_num = (String) contractMap.get("contract_num");
        String m_name = (String) contractMap.get("manager_name");
        String enterprise = (String) contractMap.get("enterprise");
        String sc = (String) contractMap.get("sc");
        sj.add("contract number: " + c_num);
        sj.add("enterprise: " + enterprise);
        sj.add("manager: " + m_name);
        sj.add("supply center: " + sc);
        sj.add(String.format("%-70s", "product_model") + String.format("%-20s","salesman")
                + String.format("%-10s", "quantity") + String.format("%-12s","unit_price")
                + String.format("%-25s","estimated_delivery_date") + String.format("%-25s","lodgement_date"));
        for (Map<String,Object> map : contractMapList) {
            String model = (String) map.get("model");
            String salesman = (String) map.get("salesman_name");
            Integer quantity = (Integer) map.get("quantity");
            Date e_date = (Date) map.get("estimated_delivery_date");
            Date l_date = (Date) map.get("lodgement_date");
            Integer price = (Integer) map.get("unit_price");
            sj.add(String.format("%-70s", model.trim()) + String.format("%-20s", salesman.trim())
                    + String.format("%-10d", quantity) + String.format("%-12d",price)
                    + String.format("%-25s",e_date.toString()) + String.format("%-25s",l_date.toString()));
        }
        String res = sj.toString();
//        res = res.replace(" ","&nbsp;");
        return res;
    }

    /**
     * 新增数据
     *
     * @param contract 实例对象
     */
    @Override
    public void insert(Contract contract) {
        this.contractDao.insert(contract);
    }

    /**
     * 修改数据
     *
     * @param contract 实例对象
     * @return 实例对象
     */
    @Override
    public Contract update(Contract contract) {
        this.contractDao.update(contract);
        return this.queryById(contract.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.contractDao.deleteById(id) > 0;
    }
}