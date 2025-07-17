package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import java.util.List;

public interface CategoryService {

    /**
     * New Category
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * Category Page Query
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * Delete Category by Id
     * @param id
     */
    void deleteById(Long id);

    /**
     * Update Category
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * Enable、Disable Category
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * Query Category by Type
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
