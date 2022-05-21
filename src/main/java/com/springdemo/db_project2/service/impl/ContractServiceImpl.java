package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Contract;
import com.springdemo.db_project2.dao.ContractDao;
import com.springdemo.db_project2.service.ContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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