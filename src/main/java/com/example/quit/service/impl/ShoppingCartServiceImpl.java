package com.example.quit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.entity.Employee;
import com.example.quit.entity.User;
import com.example.quit.mapper.EmployMapper;
import com.example.quit.mapper.UserMapper;
import com.example.quit.service.EmployeeService;
import com.example.quit.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
