package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryPageQueryDTO implements Serializable {

    // Page number
    private int page;

    // Number of records per page
    private int pageSize;

    // Category name
    private String name;

    // Category type 1 Dish category 2 Setmeal category
    private Integer type;

}
