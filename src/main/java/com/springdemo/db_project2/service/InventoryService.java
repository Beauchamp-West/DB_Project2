package com.springdemo.db_project2.service;

import com.springdemo.db_project2.entity.Inventory;

import java.util.List;
import java.util.Map;

/**
 * (Inventory)表服务接口
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
public interface InventoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Inventory queryById(Integer id);

    /**
     * 查询全部数据
     *
     * @return 对象列表
     */
    List<Inventory> queryAll();

    /**
     * number of never sold product models
     *
     * @return count of the never-sold products
     */
    Long getNeverSoldProductCount();


    /**
     * Find the models with the highest sold quantity, and the number of sales
     *
     * @return model_name, quantity
     */
    List<Map<String,Object>> getFavoriteProductModel();


    /**
     * For each supply center, calculate the average quantity of the remaining product models. The
     * results should be ordered by the name of the supply centers and rounded to one decimal place.
     *
     * @return supply_center, average
     */
    List<Map<String,Object>> getAvgStockByCenter();

    /**
     * Find a product according to the product number and return the current inventory capacity of each
     * product model in each supply center.
     *
     * @param productNum product number
     * @return supply_center, product_number, product_model, purchase_prise, quantity
     */
    List<Map<String,Object>> getProductByNumber(String productNum);

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    String insert(Inventory inventory);

    /**
     * 通过单次销量，指定企业和产品型号更新库存
     *
     * @param sold number of sales once
     * @param quantity
     * @param sales
     * @param model product_model
     * @param enterprise enterprise name
     * @return 更新信息
     */
    String updateBySold(Integer sold, Integer quantity, Integer sales, String model, String enterprise);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 查出指定enterprise和model对应inventory
     *
     * @param name enterprise name
     * @param model product_model
     * @return 对象列表
     */
    Inventory selectByEnterpriseAndModel(String name, String model);

}