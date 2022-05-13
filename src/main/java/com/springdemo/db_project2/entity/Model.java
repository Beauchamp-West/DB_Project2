package com.springdemo.db_project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Model)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:47:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    
    private Integer id;
    
    private String number;
    
    private String model;
    
    private String name;
    
    private Integer unitPrice;

}