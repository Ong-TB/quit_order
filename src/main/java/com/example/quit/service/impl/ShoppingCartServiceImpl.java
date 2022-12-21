package com.example.quit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.entity.ShoppingCart;
import com.example.quit.mapper.ShoppingCartMapper;
import com.example.quit.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
