package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:tan hao
 * Date: 2023-11-15 09:37
 * Description:
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "购物车模块")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 查看购物车
     */
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<?> list(){
        return Result.success(shoppingCartService.showShoppingCart());
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result<?> clean() {
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }

}
