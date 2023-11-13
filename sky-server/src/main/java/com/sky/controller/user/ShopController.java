package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:tan hao
 * Date: 2023-11-13 20:36
 * Description:
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "店铺相关的接口")
public class ShopController {
    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
