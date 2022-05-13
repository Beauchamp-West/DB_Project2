package com.springdemo.db_project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    
    private Integer id;
    
    private String contractNum;
    
    private String enterprise;
    
    private Integer contractManager;

    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date contractDate;

    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date estimatedDeliveryDate;

    @DateTimeFormat(pattern = "M/d/yyyy")
    private Date lodgementDate;
    
    private String contractType;

}