package com.example.service;

import com.example.entity.Qa;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.QaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QaService extends ServiceImpl<QaMapper, Qa> {

    @Resource
    private QaMapper qaMapper;

}
