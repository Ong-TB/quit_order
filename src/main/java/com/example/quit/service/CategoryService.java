package com.example.quit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.quit.common.CustomException;
import com.example.quit.entity.Category;

public interface CategoryService extends IService<Category> {
    void myRemove(Long id);
}
