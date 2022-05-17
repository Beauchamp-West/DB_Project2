package com.springdemo.db_project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * (Orders)实体类
 *
 * @author makejava
 * @since 2022-05-13 02:49:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    
    private Integer id;
    
    private String contractNum;
    
    private String enterprise;
    
    private String productModel;
    
    private Integer quantity;
    
    private Integer contractManager;

    private LocalDate contractDate;

    private LocalDate estimatedDeliveryDate;

    private LocalDate lodgementDate;
    
    private Integer salesmanNum;
    
    private String contractType;

}