package com.example.quit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quit.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployMapper extends BaseMapper<Employee> {

}
