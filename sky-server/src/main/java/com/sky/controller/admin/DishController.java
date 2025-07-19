package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Dish Management
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "Dish related interface")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("New dishes")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("New dishes: {}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * Dish paging query
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("Dish paging query")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("Dish paging query: {}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Batch delete dishes
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("Batch delete dishes")
    public Result delete(@RequestParam List<Long> ids){
        log.info("Batch delete dishes: {}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * Query dishes by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Query dishes by id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("Query dishes by id：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * Edit dishes
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("Edit dishes")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("Edit dishes：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * Query dishes by category id
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("Query dishes by category id")
    public Result<List<Dish>> list(Long categoryId) {
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * Dishes on and off sale
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("Dishes on and off sale")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);
        return Result.success();
    }
}
