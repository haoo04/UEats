package com.sky.dto;

import com.sky.entity.SetmealDish;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetmealDTO implements Serializable {

    private Long id;

    // Category id
    private Long categoryId;

    // Setmeal name
    private String name;

    // Setmeal price
    private BigDecimal price;

    // Status 0: Disabled 1: Enabled
    private Integer status;

    // Description
    private String description;

    // Image
    private String image;

    // Setmeal dish relationship
    private List<SetmealDish> setmealDishes = new ArrayList<>();

}
