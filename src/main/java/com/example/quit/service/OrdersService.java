package com.example.quit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.quit.entity.AddressBook;
import com.example.quit.entity.Order;

public interface OrderService extends IService<Order> {
    void submit(Order order);
}
