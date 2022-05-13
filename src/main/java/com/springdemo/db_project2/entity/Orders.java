package com.springdemo.db_project2.entity;

import java.io.Serializable;

/**
 * (Orders)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:49:00
 */
public class Orders implements Serializable {
    private static final long serialVersionUID = 901721405808252915L;
    
    private Integer id;
    
    private String contractNum;
    
    private String enterprise;
    
    private String productModel;
    
    private Integer quantity;
    
    private Integer contractManager;
    
    private Object contractDate;
    
    private Object estimatedDeliveryDate;
    
    private Object lodgementDate;
    
    private Integer salesmanNum;
    
    private String contractType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getContractManager() {
        return contractManager;
    }

    public void setContractManager(Integer contractManager) {
        this.contractManager = contractManager;
    }

    public Object getContractDate() {
        return contractDate;
    }

    public void setContractDate(Object contractDate) {
        this.contractDate = contractDate;
    }

    public Object getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Object estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Object getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(Object lodgementDate) {
        this.lodgementDate = lodgementDate;
    }

    public Integer getSalesmanNum() {
        return salesmanNum;
    }

    public void setSalesmanNum(Integer salesmanNum) {
        this.salesmanNum = salesmanNum;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

}