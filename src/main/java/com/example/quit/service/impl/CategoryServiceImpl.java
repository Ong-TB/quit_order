package com.example.quit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.common.CustomException;
import com.example.quit.entity.Category;
import com.example.quit.entity.Dish;
import com.example.quit.entity.Setmeal;
import com.example.quit.mapper.CategoryMapper;
import com.example.quit.service.CategoryService;
import com.example.quit.service.DishService;
import com.example.quit.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Set;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService ds;
    @Autowired
    SetmealService ss;

    @Override
    public void myRemove(Long id){
        LambdaQueryWrapper<Dish> dishLqw = new LambdaQueryWrapper<>();
        dishLqw.eq(Dish::getCategoryId, id);
        int dishCount = ds.count(dishLqw);
        if(dishCount > 0){
            throw new CustomException("Found " + dishCount + "dishes in category.");
        }
        LambdaQueryWrapper<Setmeal> setmealLqw = new LambdaQueryWrapper<>();
        setmealLqw.eq(Setmeal::getCategoryId, id);
        int setmealCount = ss.count(setmealLqw);
        if(setmealCount > 0){
            throw new CustomException("Found " + setmealCount + "setmeals in category.");
        }
        super.removeById(id);
    }
}
