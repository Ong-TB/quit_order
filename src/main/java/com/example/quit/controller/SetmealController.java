package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.quit.common.Result;
import com.example.quit.entity.*;
import com.example.quit.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;

    @PostMapping
    public Result<String> addSetmeal(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDishes(setmealDto);
        return Result.success("successfully added setmeal.");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        Page pageInfo = setmealService.pageWithCategory(page, pageSize, name);
        return Result.success(pageInfo);
    }

    @DeleteMapping
    public Result<String> deleteWithDishes(@RequestParam List<Long> ids) {
        setmealService.deleteWithDishes(ids);
        return Result.success("successfully deleted setmeal.");
    }

    @GetMapping("/list")
    public Result<List<Setmeal>> listByCategory(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId, setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        List<Setmeal> list = setmealService.list(lqw);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    public Result<List<DishDto>> getSetmealDishes(@PathVariable Long id) {
        List<DishDto> dishes = setmealService.getSetmealDishes(id);
        return Result.success(dishes);
    }
}
