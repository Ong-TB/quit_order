package com.example.quit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.quit.entity.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
