package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:tan hao
 * Date: 2023-11-15 13:11
 * Description:
 */

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "用户订单模块")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<?> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单：{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        // 现无 wx 后台访问，直接调用 paySuccess，直接支付成功
        // 业务处理，修改订单状态、来单提醒
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success();

        // 调用微信支付接口
        // 通过httpClient调用微信下单接口，返回预支付标识，将组合数据再次签名
        // 然后封装支付参数并返回  之后用户确认支付，小程序调起微信支付
        // 支付成功后 wx 会回调 /paySuccess 路径（前面调用微信下单接口所指定的）来推送支付结果给后端
        // 在paySuccessNotify接口中会调用orderService.paySuccess(...)来进行后续业务

//        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
//        log.info("生成预支付交易单：{}", orderPaymentVO);
//        return Result.success(orderPaymentVO);
    }

    /**
     * 历史订单查询
     * @param status   订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
     */
    @GetMapping("/historyOrders")
    public Result<?> page(int page, int pageSize, Integer status) {
        PageResult pageResult = orderService.pageQuery4User(page, pageSize, status);
        return Result.success(pageResult);
    }

    /**
     * 查询订单详情
     * @param id
     */
    @GetMapping("/orderDetail/{id}")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel/{id}")
    public Result<?> cancel(@PathVariable("id") Long id) {
        orderService.userCancelById(id);
        return Result.success();
    }

    /**
     * 再来一单
     */
    @PostMapping("/repetition/{id}")
    public Result<?> repetition(@PathVariable("id") Long id) {
        orderService.repetition(id);
        return Result.success();
    }
}
