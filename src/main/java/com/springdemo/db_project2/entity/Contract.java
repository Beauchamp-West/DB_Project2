package com.springdemo.db_project2.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Contract implements Serializable {
    private static final long serialVersionUID = -95380530230535469L;
    
    private Integer id;
    
    private String contractNum;
    
    private String enterprise;
    
    private Integer contractManager;

    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date contractDate;
    
    private Date estimatedDeliveryDate;
    
    private Date lodgementDate;
    
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

    public Integer getContractManager() {
        return contractManager;
    }

    public void setContractManager(Integer contractManager) {
        this.contractManager = contractManager;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Date getLodgementDate() {
        return lodgementDate;
    }

    public void setLodgementDate(Date lodgementDate) {
        this.lodgementDate = lodgementDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

}