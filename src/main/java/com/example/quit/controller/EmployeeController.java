package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.quit.common.Result;
import com.example.quit.entity.Employee;
import com.example.quit.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest req, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.eq(Employee::getUsername, employee.getUsername());

        Employee emp = employeeService.getOne(qw);

        if(emp == null){
            return Result.error("Failed to login, no employee found.");
        }

        if (!emp.getPassword().equals(password)) {
            return Result.error("Failed to login, incorrect password.");
        }

        if (emp.getStatus() == 0) {
            return Result.error("Failed to login, employee blocked.");
        }

        req.getSession().setAttribute("employee", emp.getId());

        return Result.success(emp);

    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return Result.success("Successfully logged out.");
    }

    @PostMapping
    public Result<String> addEmployee(@RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return Result.success("Successfully added.");
    }

    @GetMapping("/page")
    public Result<Page> getAllEmployees(int page, int pageSize, String name) {
        // page constructor
        Page pageInfo = new Page(page, pageSize);

        // condition constructor
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper();
        lqw.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        lqw.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, lqw);

        return Result.success(pageInfo);
    }

    @GetMapping("/{id}")
    public Result<Employee> getEmployee(@PathVariable Long id) {
        Employee emp = employeeService.getById(id);
        return Result.success(emp);
    }

    @PutMapping
    public Result<String> updateEmployee(HttpServletRequest req, @RequestBody Employee employee){
        employeeService.updateById(employee);
        return Result.success("Successfully updated.");
    }
}
