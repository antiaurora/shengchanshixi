package com.example.service;

import com.example.entity.Users;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.UsersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UsersService extends ServiceImpl<UsersMapper, Users> {

    @Resource
    private UsersMapper usersMapper;

}
