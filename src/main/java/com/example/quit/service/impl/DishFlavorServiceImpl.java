package com.example.quit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.entity.Dish;
import com.example.quit.entity.DishFlavor;
import com.example.quit.mapper.DishFlavorMapper;
import com.example.quit.mapper.DishMapper;
import com.example.quit.service.DishFlavorService;
import com.example.quit.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @author Wang Zhiming
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
