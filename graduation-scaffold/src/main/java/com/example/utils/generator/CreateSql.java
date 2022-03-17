package com.example.utils.generator;

import cn.hutool.core.io.FileUtil;

import java.util.List;

/**
 * @author xiaqing
 * @date 2021/4/1 13:40
 * @description 自动生成sql语句（懒人专属）
 */
public class CreateSql {
    private static final String TABLE_NAME = "t_salary";   // 表名，必填

    // ===============================上面是必填的配置 ===========================//

    public static final String BASE_PATH = System.getProperty("user.dir") + "/src/main/java/com/example/";   // 基础包名
    public static final String TEMPLATE = "create_table.template";  // 配置文件位置
    public static final String SPACE2 = "  ";



    public static void main(String[] args) {
        List<String> createTableLines = FileUtil.readUtf8Lines(BASE_PATH + "utils/generator/template/" + TEMPLATE);


        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE `").append(TABLE_NAME).append("` (\n")
                .append(SPACE2).append("`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',\n");  // 默认使用id作为主键，bigint类型，自动递增，有需要的自行修改
        for (String line : createTableLines) {
            String[] lineArr = line.split(" ");
            builder.append(SPACE2).append("`").append(lineArr[0]).append("` ").append(lineArr[1]).append(" DEFAULT NULL COMMENT ").append(lineArr[2]).append(",\n");
        }
        builder.append(SPACE2).append("PRIMARY KEY (`id`)\n")
                .append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");


        System.out.println(builder);   // sql语句打印到控制台，直接复制，然后粘贴到Navicat或者sqlYong里面执行即可
    }

}
