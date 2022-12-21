package com.example.quit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.common.CustomException;
import com.example.quit.entity.Category;
import com.example.quit.entity.Dish;
import com.example.quit.entity.DishDto;
import com.example.quit.entity.DishFlavor;
import com.example.quit.mapper.DishMapper;
import com.example.quit.service.CategoryService;
import com.example.quit.service.DishFlavorService;
import com.example.quit.service.DishService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dfs;
    @Autowired
    CategoryService categoryService;

    @Override
    public void saveWithFlavors(DishDto dishDto) {
        this.save(dishDto);
        Long dishId = dishDto.getId();
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        for (DishFlavor flavor : dishFlavors) {
            flavor.setDishId(dishId);
        }
        dfs.saveBatch(dishFlavors);
    }

    @Override
    public DishDto getWithFlavors(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, id);
        List<DishFlavor> dishFlavors = dfs.list(lqw);
        dishDto.setFlavors(dishFlavors);
        return dishDto;
    }

    @Override
    public void updateWithFlavors(DishDto dishDto) {
        this.updateById(dishDto);
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dishDto.getId());
        dfs.remove(lqw);
        dishFlavors = dishFlavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dfs.saveBatch(dishFlavors);
    }

    @Override
    public Page pageWithCategory(int page, int pageSize, String name) {
        Page pageInfo = new Page(page, pageSize);

        // condition constructor
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper();
        lqw.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        lqw.orderByDesc(Dish::getUpdateTime);

        this.page(pageInfo, lqw);
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long id = item.getCategoryId();
            Category category = categoryService.getById(id);
            dishDto.setCategoryName(category.getName());
            return dishDto;
        }).collect(Collectors.toList());
        pageInfo.setRecords(list);
        return pageInfo;
    }

    @Override
    public void deleteWithFlavors(List<Long> ids) {
        LambdaQueryWrapper<Dish> dishLqw = new LambdaQueryWrapper<>();
        dishLqw.in(Dish::getId, ids);
        dishLqw.eq(Dish::getStatus, 1);
        int count = this.count(dishLqw);
        if (count > 0) {
            throw new CustomException("U selected on-sale dishes.");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<DishFlavor> dfLqw = new LambdaQueryWrapper<>();
        dfLqw.in(DishFlavor::getDishId, ids);
        dfs.remove(dfLqw);
    }

    @Override
    public List<DishDto> listWithFlavors(Dish dish) {
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Dish::getCategoryId, dish.getCategoryId());
        lqw.eq(dish.getStatus() != null, Dish::getStatus, dish.getStatus());
        List<Dish> dishes = this.list(lqw);
        List<DishDto> list = dishes.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());

            LambdaQueryWrapper<DishFlavor> dflqw = new LambdaQueryWrapper<>();
            dflqw.eq(DishFlavor::getDishId, dishDto.getId());
            List<DishFlavor> flavors = dfs.list(dflqw);
            dishDto.setFlavors(flavors);

            return dishDto;
        }).collect(Collectors.toList());
        return list;
    }
}
