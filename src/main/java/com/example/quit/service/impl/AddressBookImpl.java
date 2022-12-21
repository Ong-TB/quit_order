package com.example.quit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.entity.AddressBook;
import com.example.quit.mapper.AddressBookMapper;
import com.example.quit.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
