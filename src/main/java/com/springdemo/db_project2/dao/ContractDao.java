package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Contract)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 02:41:16
 */
@Mapper
public interface ContractDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Contract queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Contract> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 查询全部合同
     *
     * @return 对象列表
     */
    @Select("select * from contract")
    List<Contract> queryAll();

    /**
     * 新增数据
     *
     * @param contract 实例对象
     * @return 影响行数
     */
    int insert(Contract contract);

    /**
     * 修改数据
     *
     * @param contract 实例对象
     * @return 影响行数
     */
    int update(Contract contract);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}