package com.example.entity;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;


@Data
@TableName("users")
public class Users extends Model<Users> {
    /**
      * 主键
      */
    @TableId(value = "uid", type = IdType.AUTO)
    private Long uid;

    /**
      * 账号 
      */
    private String account;

    /**
      * 密码 
      */
    private String password;

    /**
      * 姓名 
      */
    private String name;

    /**
      * 性别 
      */
    private String gender;

    /**
      * 生日 
      */
    private String birthday;

    /**
      * 邮箱 
      */
    private String email;

    /**
      * 手机 
      */
    private String mobile;

    /**
      * 用户头像url 
      */
    private String avatarUrl;

    /**
      * 最后登录时间 
      */
    private String lasttime;

    /**
      * 登录次数 
      */
    private Integer logincount;

    /**
      * 是否有效（无效0有效1） 
      */
    private Integer validstate;

    /**
      * 上次选择的城市 
      */
    private String city;

}