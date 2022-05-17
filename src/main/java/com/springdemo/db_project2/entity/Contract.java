package com.springdemo.db_project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    
    private Integer id;
    
    private String contractNum;
    
    private String enterprise;
    
    private Integer contractManager;

    private LocalDate contractDate;

    private LocalDate estimatedDeliveryDate;

    private LocalDate lodgementDate;
    
    private String contractType;

}