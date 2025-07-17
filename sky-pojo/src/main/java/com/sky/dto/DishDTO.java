package com.sky.dto;

import com.sky.entity.DishFlavor;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDTO implements Serializable {

    private Long id;
    // Dish name
    private String name;
    // Dish category id
    private Long categoryId;
    // Dish price
    private BigDecimal price;
    // Image
    private String image;
    // Description
    private String description;
    // 0 Stop sale 1 On sale
    private Integer status;
    // Flavor
    private List<DishFlavor> flavors = new ArrayList<>();

}
