package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.quit.common.Result;
import com.example.quit.entity.Category;
import com.example.quit.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wang Zhiming
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public Result<Page> getAllCategories(int page, int pageSize) {
        log.info("haha category");
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(Category::getSort);
        categoryService.page(pageInfo, lqw);
        return Result.success(pageInfo);
    }

    @PostMapping
    public Result<String> addCategory(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success("successfully added category.");
    }

    @PutMapping
    public Result<String> updateCategory(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success("successfully updated category.");
    }

    @DeleteMapping
    public Result<String> deleteCategory(long ids) {
        categoryService.myRemove(ids);
        return Result.success("successfully deleted category.");
    }

    @GetMapping("/list")
    public Result<List<Category>> listCategory(Integer type) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        lqw.eq(type != null, Category::getType, type);
        List<Category> list = categoryService.list(lqw);
        return Result.success(list);
    }
}
