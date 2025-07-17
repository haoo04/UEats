package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetmealPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String name;

    // Category id
    private Integer categoryId;

    // Status 0: Disabled 1: Enabled
    private Integer status;

}
