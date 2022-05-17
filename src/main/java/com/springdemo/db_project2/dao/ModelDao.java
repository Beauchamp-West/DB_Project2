package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.Model;
import com.springdemo.db_project2.provider.ModelProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * (Model)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 02:47:40
 */
@Mapper
public interface ModelDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Model selectById(Integer id);

    /**
     * 通过model查询
     *
     * @param m model名
     * @return 对象列表
     */
    @Select("select * from model where model = #{m}")
    List<Model> queryByModel(String m);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param model 实例对象
     * @return 对象列表
     */
    List<Model> selectAll(Model model);

    /**
     * 新增数据
     *
     * @param model 实例对象
     * @return 影响行数
     */
    int insert(Model model);

    /**
     * 修改数据
     *
     * @param model 实例对象
     * @return 影响行数
     */
    int update(Model model);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 批量新增数据
     *
     * @param models 新增数据列表
     */
    @InsertProvider(type = ModelProvider.class, method = "importModels")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void batchInsert(@Param("list") List<Model> models);
}