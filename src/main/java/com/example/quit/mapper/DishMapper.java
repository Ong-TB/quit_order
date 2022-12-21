package com.example.quit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quit.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
