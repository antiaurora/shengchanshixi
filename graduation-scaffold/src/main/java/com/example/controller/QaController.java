package com.example.controller;

import com.example.common.Result;
import com.example.entity.Qa;
import com.example.service.QaService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/qa")
public class QaController {
    @Resource
    private QaService qaService;
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
    public Result<?> save(@RequestBody Qa qa) {
        return Result.success(qaService.save(qa));
    }

    @PutMapping
    public Result<?> update(@RequestBody Qa qa) {
        return Result.success(qaService.updateById(qa));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        qaService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(qaService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(qaService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Qa> query = Wrappers.<Qa>lambdaQuery().like(Qa::getName, name).orderByDesc(Qa::getId);;
        return Result.success(qaService.page(new Page<>(pageNum, pageSize), query));
    }

}
