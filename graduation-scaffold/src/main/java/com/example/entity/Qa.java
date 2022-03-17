package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("t_qa")
public class Qa extends Model<Qa> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 问题 
      */
    private String name;

    /**
      * 答案 
      */
    private String answer;

    /**
      * 使用次数 
      */
    private Integer count;

    /**
      * 其他 
      */
    private String others;

}