package com.example.quit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quit.entity.Dish;
import com.example.quit.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
