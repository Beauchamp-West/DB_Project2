package com.springdemo.db_project2.entity;

import lombok.*;

/**
 * (SupplyCenter)实体类
 *
 * @author makejava
 * @since 2022-05-12 17:37:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyCenter {
    
    private Integer id;
    
    private String name;

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

    @Override
    public String toString() {
        return "SupplyCenter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}