package com.springdemo.db_project2.entity;

import java.io.Serializable;

/**
 * (Staff)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:50:08
 */
public class Staff implements Serializable {
    private static final long serialVersionUID = -35743357191694224L;
    
    private Integer id;
    
    private String name;
    
    private Integer age;
    
    private String gender;
    
    private Integer number;
    
    private String supplyCenter;
    
    private String mobileNumber;
    
    private String type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSupplyCenter() {
        return supplyCenter;
    }

    public void setSupplyCenter(String supplyCenter) {
        this.supplyCenter = supplyCenter;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}