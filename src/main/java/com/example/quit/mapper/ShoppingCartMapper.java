package com.example.quit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quit.entity.Dish;
import com.example.quit.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
