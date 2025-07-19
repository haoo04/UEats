package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * Add new dishes and corresponding flavors
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * Dish paging query
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * Batch delete dishes
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * Query dishes by id
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * Edit dishes
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * Query dishes by category id
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * Conditional query of dishes and flavors
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * Dishes on and off sale
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
