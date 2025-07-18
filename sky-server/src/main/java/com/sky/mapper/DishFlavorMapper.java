package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * Batch insert flavor data
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * Delete the corresponding flavor data according to the dish ID
     * @param dishId
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * Batch delete the associated flavor data according to the dish ID set
     * @param ids
     */
    void deleteByDishIds(List<Long> ids);

    /**
     * Query flavor data based on dish id
     * @param id
     * @return
     */
    List<DishFlavor> getByDishId(Long id);
}
