package com.springdemo.db_project2.provider;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Contract(复杂数据库操作方法提供层)
 */
public class ContractProvider {
    public String getContractInfo (@Param("num") String contract_num) {
        String sql = "select contract_num,manager_name,enterprise,sc,t3.model,salesman_name,quantity, " +
                "estimated_delivery_date, lodgement_date,model.unit_price " +
                "from (select contract_num,manager_name,enterprise,sc,t2.product_model as model, " +
                "salesman_name,quantity,estimated_delivery_date,lodgement_date\n" +
                "from (select contract_num,manager_name,enterprise,t1.supply_center as sc, " +
                "product_model,name as salesman_name,estimated_delivery_date,lodgement_date " +
                "from (select contract_num,name as manager_name,enterprise,supply_center, " +
                "product_model,salesman_num,estimated_delivery_date,lodgement_date " +
                "from orders join staff on orders.contract_manager = staff.number " +
                "where contract_num = #{num}) as t1 join staff on t1.salesman_num = staff.number) as t2 " +
                "join inventory on t2.product_model = inventory.product_model ) as t3 " +
                "join model on t3.model = model.model";
        return sql;
    }
}
