package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.quit.common.BaseContext;
import com.example.quit.common.Result;
import com.example.quit.entity.ShoppingCart;
import com.example.quit.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public Result<String> addToCart(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart) {
        Long userId = (Long) request.getSession().getAttribute("user");
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId())
                .eq(ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor());
        ShoppingCart one = shoppingCartService.getOne(lqw);
        if (one == null) {
            one = shoppingCart;
            one.setCreateTime(LocalDateTime.now());
            one.setUserId(userId);
        } else {
            one.setNumber(one.getNumber() + 1);
        }
        shoppingCartService.saveOrUpdate(one);
        return Result.success("Successfully added to shopping cart.");
    }

    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(HttpServletRequest request){
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, request.getSession().getAttribute("user"));
        return Result.success(shoppingCartService.list(lqw));
    }

    @DeleteMapping("/clean")
    public Result<String> clean() {
        //条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        queryWrapper.eq(userId != null, ShoppingCart::getUserId, userId);
        //删除当前用户id的所有购物车数据
        shoppingCartService.remove(queryWrapper);
        return Result.success("成功清空购物车");
    }
}
