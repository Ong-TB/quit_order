package com.example.quit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.quit.entity.DishDto;
import com.example.quit.entity.Setmeal;
import com.example.quit.entity.SetmealDish;
import com.example.quit.entity.SetmealDto;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveWithDishes(SetmealDto setmealDto);
    Page pageWithCategory(int page, int pageSize, String name);

    void deleteWithDishes(List<Long> ids);

    List<DishDto> getSetmealDishes(Long id);
}
