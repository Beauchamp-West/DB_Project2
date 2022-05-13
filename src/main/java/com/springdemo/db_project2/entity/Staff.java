package com.springdemo.db_project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Staff)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:50:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    
    private Integer id;
    
    private String name;
    
    private Integer age;
    
    private String gender;
    
    private Integer number;
    
    private String supplyCenter;
    
    private String mobileNumber;
    
    private String type;

}