package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * Query the corresponding set meal ID based on the dish ID
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * Save the relationship between meal packages and dishes
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * Delete the data in the meal relationship table
     * @param id
     */
    void deleteBySetmaleId(Long id);

    /**
     * Query dish information based on set meal information
     * @param id
     * @return
     */
    List<SetmealDish> getBySetmealId(Long id);
}
