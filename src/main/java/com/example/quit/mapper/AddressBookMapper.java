package com.example.quit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quit.entity.AddressBook;
import com.example.quit.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
