package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // Type: 1 Dish category 2 Setmeal category
    private Integer type;

    // Category name
    private String name;

    // Sort
    private Integer sort;

    // Category status 0 Disabled 1 Enabled
    private Integer status;

    // Create time
    private LocalDateTime createTime;

    // Update time
    private LocalDateTime updateTime;

    // Create user
    private Long createUser;

    // Update user
    private Long updateUser;
}
