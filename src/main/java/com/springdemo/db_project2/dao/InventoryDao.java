package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.Inventory;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * (Inventory)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
@Mapper
public interface InventoryDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Inventory queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Inventory> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * select all inventories
     *
     * @return 对象列表
     */
    @Select("select * from inventory order by id")
    List<Inventory> queryAll();

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 影响行数
     */
    @Insert("insert into inventory (supply_center, product_model, supply_staff, date, purchase_price, quantity, sales)" +
            " values(#{i.supplyCenter}, #{i.productModel}, #{i.supplyStaff}, #{i.date}, " +
            "#{i.purchasePrice}, #{i.quantity}, #{i.sales})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("i") Inventory inventory);


    /**
     * 查出指定enterprise和model对应inventory
     *
     * @param name enterprise name
     * @param model product_model
     * @return 对象列表
     */
    @Select("select * from inventory " +
            "where (select supply_center from enterprise where name = #{name}) = supply_center " +
            "                and product_model = #{model}")
    List<Inventory> selectByEnterpriseAndModel(@Param("name") String name, @Param("model") String model);


    /**
     * select inventory by a pair of supply center and product model
     *
     * @param center supply center name
     * @param model product model
     * @return result list
     */
    @Select("select * from inventory where product_model = #{model} and supply_center = #{center}")
    List<Inventory> selectByCenterAndModel(@Param("center") String center, @Param("model") String model);


    @Select("select * from inventory where product_model = #{model} and supply_center = " +
            "(select distinct supply_center from enterprise join orders on orders.enterprise = enterprise.name " +
            "where contract_num = #{c_num})")
    List<Inventory> selectByModelAndContractNum(@Param("model") String model, @Param("c_num") String contract_num);


    @Select("select count(*) as cnt from (select product_model, sum(sales) as sum " +
            "from inventory group by product_model having sum(sales) = 0) t")
    Map<String,Object> selectNeverSoldCnt();

    @Select("select maximum , product_model from (select max(sum) as maximum from " +
            "(select sum(sales) as sum from inventory group by product_model)as t1) as t2 " +
            "join (select sum(sales) as sum, product_model from inventory group by product_model)as t1 " +
            "on t1.sum = t2.maximum")
    List<Map<String, Object>> selectFavorite();

    @Select("select round(sum(quantity)*1.0/count(*),1) as average, supply_center from inventory " +
            "group by supply_center order by supply_center;")
    List<Map<String,Object>> selectAvgStockByCenter();

    @Select("select supply_center, product_model, quantity " +
            "from inventory i join model m on i.product_model = m.model where m.number = #{num}")
    List<Map<String,Object>> selectProductByNumber(@Param("num") String productNum);

    /**
     * 通过单次销量，指定企业和产品型号更新库存
     *
     *  @param sold once sales
     * @param quantity original quantity
     * @param sales original sales
     * @param model product_model
     * @param enterprise enterprise name
     */
    @Update("update inventory set quantity = (#{quantity} - #{sold}), sales = (#{sales} + #{sold})" +
            "where product_model = #{model} " +
            "and supply_center = (select supply_center from enterprise where name = #{enterprise})")
    void updateBySold(@Param("sold") Integer sold, @Param("quantity") Integer quantity, @Param("sales") Integer sales,
    @Param("model") String model, @Param("enterprise") String enterprise);


    @Update("update inventory set quantity = #{quantity}, sales = #{sales} " +
            "where product_model = #{model} and supply_center = " +
            "(select distinct supply_center from enterprise join orders on orders.enterprise = enterprise.name " +
            "where contract_num = #{c_num})")
    void updateQuantityByModelAndContract(@Param("quantity") Integer quantity, @Param("sales") Integer sales,
                                          @Param("model") String model, @Param("c_num") String contractNum);


    /**
     * update quantity by adding the new quantity
     *
     * @param quantity original quantity
     * @param newQuantity new quantity
     * @param date new date
     * @param model product model
     * @param center supply center name
     */
    @Update("update inventory set quantity = (#{quantity} + #{newQuantity}), date = #{date} " +
            "where product_model = #{model} and supply_center = #{center}")
    void updateQuantity(@Param("quantity") Integer quantity, @Param("newQuantity") Integer newQuantity,
                        @Param("date") LocalDate date, @Param("model") String model, @Param("center") String center);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}