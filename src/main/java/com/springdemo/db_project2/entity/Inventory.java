package com.springdemo.db_project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * (Inventory)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    
    private Integer id;
    
    private String supplyCenter;
    
    private String productModel;
    
    private Integer supplyStaff;

    private LocalDate date;
    
    private Integer purchasePrice;
    
    private Integer quantity;
    
    private Integer sales;
}