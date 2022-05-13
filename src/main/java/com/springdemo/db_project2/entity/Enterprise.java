package com.springdemo.db_project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Enterprise)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:45:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {
    
    private Integer id;
    
    private String name;
    
    private String country;
    
    private String city;
    
    private String supplyCenter;
    
    private String industry;

}