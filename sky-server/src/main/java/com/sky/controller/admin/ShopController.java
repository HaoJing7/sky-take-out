package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * Author:tan hao
 * Date: 2023-11-13 20:25
 * Description:
 */
@RestController("adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺相关的接口")
public class ShopController {
    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置店铺的营业状态
     */
    @PutMapping("/{status}")
    public Result<?> setStatus(@PathVariable String status) {
        log.info("设置店铺的营业状态为：{}", status);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY, status);
        return Result.success();
    }

    /**
     * 查询店铺的状态信息
     */
    @GetMapping("/status")
    public Result<?> getStatus() {
        log.info("获取店铺的营业状态");
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String status = (String) valueOperations.get(KEY);
        return Result.success(status);
    }
}
