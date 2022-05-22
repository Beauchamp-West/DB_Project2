package com.springdemo.db_project2.provider;

import java.util.Map;

/**
 * Orders(复杂数据库操作方法提供层)
 */
public class OrdersProvider {
    public String selectByArgs(Map<String,Object> map) {
        String contractNum = (String) map.get("c_num");
        String enterprise = (String) map.get("enterprise");
        String model = (String) map.get("model");
        String manager = (String) map.get("manager");
        String contractDate = (String) map.get("c_date");
        String estimatedDeliveryDate = (String) map.get("e_date");
        String lodgementDate = (String) map.get("l_date");
        String salesman = (String) map.get("salesman");
        String contractType = (String) map.get("c_type");
        String operation = "select * from orders ";

        if (!contractNum.equals("")) {
            operation += " where ";
            operation += " contract_num = '" + contractNum + "' ";
        }
        if (!enterprise.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " enterprise = '" + enterprise + "' ";
        }
        if (!model.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " product_model = '" + model + "' ";
        }
        if (!manager.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " contract_manager = '" + manager + "'";
        }
        if (!contractDate.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " contract_date = '" + contractDate + "' ";
        }
        if (!estimatedDeliveryDate.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " estimated_delivery_date = '" + estimatedDeliveryDate + "' ";
        }
        if (!lodgementDate.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " lodgement_date = '" + lodgementDate + "' ";
        }if (!salesman.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " salesman_num = '" + salesman + "' ";
        }if (!contractType.equals("")) {
            if (operation.length() < 25) operation += " where ";
            else operation += " and ";
            operation += " contract_type = '" + contractType + "' ";
        }

        return operation;
    }
}
