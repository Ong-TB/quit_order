package com.example.quit.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.quit.common.LoginPair;
import com.example.quit.common.Result;
import com.example.quit.entity.User;
import com.example.quit.service.UserService;
import com.example.quit.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(HttpServletRequest request, @RequestBody LoginPair phone) {
        String dst = phone.getPhone();
        String code = MailUtil.achieveCode();
        request.getSession().setAttribute(dst, code);
        try {
            MailUtil.sendTestMail(dst, code);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return Result.success("Successfully sent.");
    }

    @PostMapping("/login")
    public Result<String> login(HttpServletRequest request, @RequestBody LoginPair loginPair) {
        String trueCode = (String) request.getSession().getAttribute(loginPair.getPhone());
        String code = loginPair.getCode();
        String phone = loginPair.getPhone();
        if(!code.equals(code)){
            return Result.error("Failed to login, wrong code!");
        }
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.eq(User::getPhone, phone);
        User user = userService.getOne(lqw);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setName("User" + code);
            userService.save(user);
        }
        request.getSession().setAttribute("user", user.getId());
        return Result.success("Successfully logged in.");
    }
}
