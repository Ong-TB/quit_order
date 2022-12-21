package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.quit.common.BaseContext;
import com.example.quit.common.Result;
import com.example.quit.entity.AddressBook;
import com.example.quit.service.AddressBookService;
import com.example.quit.service.impl.AddressBookImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;
    @GetMapping("/list")
    public Result<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());

        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
        lqw.orderByDesc(AddressBook::getUpdateTime);

        List<AddressBook> addressBooks = addressBookService.list(lqw);
        return Result.success(addressBooks);
    }

    @PostMapping
    public Result<AddressBook> addAddress(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success(addressBook);
    }

    @PutMapping("/default")
    public Result<AddressBook> setDefaultAddress(@RequestBody AddressBook addressBook) {
        //获取当前用户id
        addressBook.setUserId(BaseContext.getCurrentId());
        //条件构造器
        LambdaUpdateWrapper<AddressBook> queryWrapper = new LambdaUpdateWrapper<>();
        //条件：当前用户的地址
        queryWrapper.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
        //将当前用户地址的is_default字段全部设为0
        queryWrapper.set(AddressBook::getIsDefault, 0);
        //执行更新操作
        addressBookService.update(queryWrapper);
        //随后再将当前地址的is_default字段设为1
        addressBook.setIsDefault(1);
        //再次执行更新操作
        addressBookService.updateById(addressBook);
        return Result.success(addressBook);
    }

    @GetMapping("/default")
    public Result<AddressBook> defaultAddress() {
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        //当前用户
        queryWrapper.eq(userId != null, AddressBook::getUserId, userId);
        //默认地址
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        return Result.success(addressBook);
    }
}
