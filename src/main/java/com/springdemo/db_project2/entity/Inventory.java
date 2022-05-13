package com.springdemo.db_project2.entity;

import java.io.Serializable;

/**
 * (Inventory)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
public class Inventory implements Serializable {
    private static final long serialVersionUID = 683392591053997979L;
    
    private Integer id;
    
    private String supplyCenter;
    
    private String productModel;
    
    private Integer supplyStaff;
    
    private Object date;
    
    private Integer purchasePrice;
    
    private Integer quantity;
    
    private Integer sales;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Integer getSupplyStaff() {
        return supplyStaff;
    }

    public void setSupplyStaff(Integer supplyStaff) {
        this.supplyStaff = supplyStaff;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Integer getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Integer purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

}