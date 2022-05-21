package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.Contract;
import java.util.List;

/**
 * (Contract)表服务接口
 *
 * @author makejava
 * @since 2022-05-13 02:41:16
 */
public interface ContractService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Contract queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<Contract> queryAll();

    /**
     * total number of contracts
     *
     * @return count
     */
    Long getContractCount();

    /**
     * 新增数据
     *
     * @param contract 实例对象
     */
    void insert(Contract contract);

    /**
     * 修改数据
     *
     * @param contract 实例对象
     * @return 实例对象
     */
    Contract update(Contract contract);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}