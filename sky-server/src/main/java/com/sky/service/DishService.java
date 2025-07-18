package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {

    /**
     * Add new dishes and corresponding flavors
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
