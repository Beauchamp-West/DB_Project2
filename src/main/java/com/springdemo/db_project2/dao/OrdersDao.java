package com.springdemo.db_project2.dao;

import com.springdemo.db_project2.entity.Orders;
import com.springdemo.db_project2.provider.OrdersProvider;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * (Orders)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-13 02:49:00
 */
@Mapper
public interface OrdersDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Orders queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Orders> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * query all orders
     *
     */
    @Select("select * from orders")
    List<Orders> queryAll();

    @Select("select * from orders where contract_num = #{c_num} " +
            "and product_model = #{model} and salesman_num = #{s_num}")
    List<Orders> selectByContractAndModelAndSalesman(@Param("c_num") String contractNum, @Param("model") String model,
                                                     @Param("s_num") Integer salesmanNum);

    @Select("select id, quantity, product_model from (" +
            "select *, row_number() over(order by estimated_delivery_date, product_model) as rnb " +
            "from orders where contract_num = #{c_num} and salesman_num = #{s_num}) as t where rnb = #{seq}")
    @Results(id = "orderMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "quantity", property = "quantity"),
            @Result(column = "product_model", property = "productModel"),
    })
    Orders selectByContractAndSalesmanAndSeq(@Param("c_num") String contractNum,
                                             @Param("s_num") Integer salesmanNum, @Param("seq") Integer seq);

    /**
     * select count of all orders
     *
     * @return count
     */
    @Select("select count(*) as cnt from orders")
    Map<String,Object> selectCnt();

    @SelectProvider(type = OrdersProvider.class, method = "selectByArgs")
    List<Orders> selectByMultiArgs(@Param("c_num") String contractNum, @Param("enterprise") String enterprise,
                                   @Param("model") String model, @Param("manager") String manager,
                                   @Param("c_date") String contractDate, @Param("e_date") String estimatedDeliveryDate,
                                   @Param("l_date") String lodgementDate, @Param("salesman") String salesman,
                                   @Param("c_type") String contractType);

    /**
     * 新增数据
     *
     * @param orders 实例对象
     * @return 影响行数
     */
    @Insert("insert into orders (contract_num, enterprise, product_model, quantity, " +
            "contract_manager, contract_date, estimated_delivery_date, lodgement_date, " +
            "salesman_num, contract_type) values (#{o.contractNum},#{o.enterprise},#{o.productModel}," +
            "#{o.quantity},#{o.contractManager},#{o.contractDate},#{o.estimatedDeliveryDate}," +
            "#{o.lodgementDate},#{o.salesmanNum},#{o.contractType})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("o") Orders orders);

    /**
     * 修改数据
     *
     * @param orders 实例对象
     * @return 影响行数
     */
    int update(Orders orders);

    /**
     * update orders by the given contract number, product model and salesman number
     *
     * @param quantity new quantity
     * @param estimated_delivery_date new e_date
     * @param lodgement_date new l_date
     * @param contractNum given contract number
     * @param model given product model
     * @param salesmanNum given salesman number
     */
    @Update("update orders set quantity = #{q}, estimated_delivery_date = #{e_date}, lodgement_date = #{l_date} " +
            "where contract_num = #{c_num} and product_model = #{model} and salesman_num = #{s_num}")
    void updateQuantityAndDates(@Param("q") Integer quantity, @Param("e_date")LocalDate estimated_delivery_date,
                                @Param("l_date") LocalDate lodgement_date, @Param("c_num") String contractNum,
                                @Param("model") String model, @Param("s_num") Integer salesmanNum);


    @Update("update orders set contract_type = case " +
            "when lodgement_date is not null and lodgement_date <= orders.estimated_delivery_date then 'Finished' " +
            "when (lodgement_date is null and estimated_delivery_date < #{date}) " +
            "or (lodgement_date is not null and lodgement_date > orders.estimated_delivery_date) then 'Overdue' " +
            "when lodgement_date is null and estimated_delivery_date >= #{date} then 'Unfinished' end " +
            "where contract_type != 'Finished' or contract_type != 'Overdue'")
    void updateTypeByTime(@Param("date") Date date);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Delete("delete from orders where id = #{id}")
    int deleteById(Integer id);

}