package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Category Management
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "Category Management")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * New Category
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("New Category")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO){
        log.info("New Category：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * Category Page Query
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Category Page Query")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("Category Page Query：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Delete Category
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("Delete Category")
    public Result<String> deleteById(Long id){
        log.info("Delete Category：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * Update Category
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Update Category")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * Enable、Disable Category
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Enable、Disable Category")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * Query Category by Type
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("Query Category by Type")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
