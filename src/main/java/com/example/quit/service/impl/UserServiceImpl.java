package com.example.quit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.quit.entity.Employee;
import com.example.quit.mapper.EmployMapper;
import com.example.quit.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployServiceImpl extends ServiceImpl<EmployMapper, Employee> implements EmployeeService {

}
