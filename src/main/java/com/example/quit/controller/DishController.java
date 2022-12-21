package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.quit.common.Result;
import com.example.quit.entity.Category;
import com.example.quit.entity.Dish;
import com.example.quit.entity.DishDto;
import com.example.quit.entity.Employee;
import com.example.quit.mapper.DishMapper;
import com.example.quit.service.CategoryService;
import com.example.quit.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;

    @GetMapping("/page")
    public Result<Page> getAllDishes(int page, int pageSize, String name) {
        // page constructor
        Page pageInfo = dishService.pageWithCategory(page, pageSize, name);
        return Result.success(pageInfo);
    }

    @PostMapping
    public Result<String> addDish(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavors(dishDto);
        return Result.success("successfully added dish.");
    }

    @GetMapping("/{id}")
    public Result<Dish> getDish(@PathVariable Long id) {
        Dish dish = dishService.getWithFlavors(id);
        return Result.success(dish);
    }

    @PutMapping
    public Result<String> updateDish(@RequestBody DishDto dishDto){
        dishService.updateWithFlavors(dishDto);
        return Result.success("successfully updated.");
    }

    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable int status, @RequestParam("ids") List<Long> ids) {
        System.out.println(status+"..."+ids);
        List<Dish> dishes = dishService.listByIds(ids);
        dishes.forEach(dish -> dish.setStatus(status));
        dishService.updateBatchById(dishes);
        return Result.success("successfully updated status.");
    }

    @GetMapping("/list")
    public Result<List<DishDto>> listByCategory(Dish dish) {
        List<DishDto> list = dishService.listWithFlavors(dish);
        return Result.success(list);
    }

    @DeleteMapping
    public Result<String> deleteDishes(@RequestParam List<Long> ids) {
        dishService.deleteWithFlavors(ids);
        return Result.success("successfully deleted dishes.");
    }
}
