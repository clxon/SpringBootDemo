package me.lemon.boot.web;

import me.lemon.boot.bean.User;
import me.lemon.boot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("query")
    public List<User> queryForUsers() {
        return userMapper.findAll();
    }
}
