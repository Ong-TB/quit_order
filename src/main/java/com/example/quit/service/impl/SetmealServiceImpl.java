package com.example.quit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.common.CustomException;
import com.example.quit.entity.*;
import com.example.quit.mapper.SetmealMapper;
import com.example.quit.service.CategoryService;
import com.example.quit.service.DishService;
import com.example.quit.service.SetmealDishService;
import com.example.quit.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    SetmealDishService setmealDishService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishService dishService;

    public void saveWithDishes(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        Long id = setmealDto.getId();
        dishes.forEach(d -> d.setSetmealId(id));
        setmealDishService.saveBatch(dishes);
    }

    @Override
    public Page pageWithCategory(int page, int pageSize, String name) {
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        this.page(pageInfo, lqw);
        List<Setmeal> setmeals = pageInfo.getRecords();
        List<SetmealDto> list = setmeals.stream().map((item) -> {
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            setmealDto.setCategoryName(category.getName());
            return setmealDto;
        }).collect(Collectors.toList());
        pageInfo.setRecords(list);
        return pageInfo;
    }

    @Override
    public void deleteWithDishes(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> slqw = new LambdaQueryWrapper<>();
        slqw.in(Setmeal::getId, ids);
        slqw.eq(Setmeal::getStatus, 1);
        int count = this.count(slqw);
        if (count > 0) {
            throw new CustomException("U selected on-sale setmeals.");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> dlqw = new LambdaQueryWrapper<>();
        dlqw.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(dlqw);
    }

    @Override
    public List<DishDto> getSetmealDishes(Long id) {
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> setmealDishes = setmealDishService.list(lqw);
        List<DishDto> list = setmealDishes.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            Dish dish = dishService.getById(item.getDishId());
            BeanUtils.copyProperties(dish, dishDto);
            dishDto.setCopies(item.getCopies());
            return dishDto;
        }).collect(Collectors.toList());
        return list;
    }
}
