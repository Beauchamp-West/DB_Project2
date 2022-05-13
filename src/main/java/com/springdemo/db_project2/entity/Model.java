package com.springdemo.db_project2.entity;

import java.io.Serializable;

/**
 * (Model)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:47:40
 */
public class Model implements Serializable {
    private static final long serialVersionUID = 670917124430926078L;
    
    private Integer id;
    
    private String number;
    
    private String model;
    
    private String name;
    
    private Integer unitPrice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

}