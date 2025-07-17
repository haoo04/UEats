package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    // Primary key
    private Long id;

    // Type 1 Dish category 2 Setmeal category
    private Integer type;

    // Category name
    private String name;

    // Sort
    private Integer sort;

}
