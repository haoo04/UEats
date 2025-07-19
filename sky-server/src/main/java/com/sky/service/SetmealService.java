package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * New setmeal
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * Pagination query
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * Delete setmeal in batchs
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * Query setmeal by id
     * @param id
     * @return
     */
    SetmealVO getByIdWithDish(Long id);

    /**
     * Edit setmeal
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * start or stop setmeal
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * Conditional Query
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * Query dish options by id
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
