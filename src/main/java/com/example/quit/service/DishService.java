package com.example.quit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.quit.entity.Dish;
import com.example.quit.entity.DishDto;

import java.util.List;

public interface DishService extends IService<Dish> {
    void saveWithFlavors(DishDto dishDto);

    DishDto getWithFlavors(Long id);

    void updateWithFlavors(DishDto dishDto);

    Page pageWithCategory(int page, int pageSize, String name);

    void deleteWithFlavors(List<Long> ids);

    List<DishDto> listWithFlavors(Dish dish);
}
