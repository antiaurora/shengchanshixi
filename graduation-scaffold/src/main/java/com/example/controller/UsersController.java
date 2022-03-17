package com.example.controller;

import com.example.common.Result;
import com.example.entity.Users;
import com.example.service.UsersService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    @Resource
    private UsersService usersService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @PostMapping
    public Result<?> save(@RequestBody Users users) {
        return Result.success(usersService.save(users));
    }

    @PutMapping
    public Result<?> update(@RequestBody Users users) {
        return Result.success(usersService.updateById(users));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        usersService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(usersService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(usersService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Users> query = Wrappers.<Users>lambdaQuery().like(Users::getName, name).orderByDesc(Users::getUid);;
        return Result.success(usersService.page(new Page<>(pageNum, pageSize), query));
    }

}
