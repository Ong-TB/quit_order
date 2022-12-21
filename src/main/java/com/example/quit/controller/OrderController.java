package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.quit.common.BaseContext;
import com.example.quit.common.Result;
import com.example.quit.entity.Category;
import com.example.quit.entity.Orders;
import com.example.quit.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    OrdersService ordersService;
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders) {
        log.info("order:{}", orders);
        ordersService.submit(orders);
        return Result.success("用户下单成功");
    }

    @GetMapping("/userPage")
    public Result<Page<Orders>> page(int page, int pageSize) {
        log.info("haha category");
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Orders::getUserId, BaseContext.getCurrentId());
        lqw.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo, lqw);
        return Result.success(pageInfo);
    }
}
