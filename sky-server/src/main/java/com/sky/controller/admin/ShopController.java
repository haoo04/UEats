package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Api(tags = "Store related interfaces")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Set the store's operating status
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("Set the store's operating status")
    public Result setStatus(@PathVariable Integer status){
        log.info("Set the store's business status to: {}", status == 1 ? "Open" : "Closed");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    /**
     * Get the store's business status
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("Get the store's business status")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        if(status == null){
            return Result.error("null");
        }
        log.info("Get the store's business status: {}", status == (1) ? "Open" : "Closed");
        return Result.success(status);
    }
}
