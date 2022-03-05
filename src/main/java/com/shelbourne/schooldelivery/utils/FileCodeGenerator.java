package com.shelbourne.schooldelivery.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

//代码生成器，可以从数据库的数据表直接生成各层代码
public class FileCodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator generator = FastAutoGenerator.create("jdbc:mysql://localhost:3306/school_delivery?serverTimezone=GMT%2b8",
                "root", "123456");
        //全局配置
        generator.globalConfig(builder -> {
            builder.author("Shelby") // 设置作者
                    .enableSwagger() // 开启 swagger 模式
                    .fileOverride() // 覆盖已生成文件
                    .outputDir("D:\\study\\school_delivery\\src\\main\\java"); // 指定输出目录
        });
        //包配置
        generator.packageConfig(builder -> {
            builder.parent("com.shelbourne.schooldelivery") // 设置父包名
                    .moduleName(null) // 设置父包模块名，设为null可以避免@RequestMapping("/user" )出现双斜杠
                    .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                            "D:\\study\\school_delivery\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
        });
        //策略配置
        generator.strategyConfig(builder -> {
            builder.addInclude("file") // 设置需要生成的表名
                    .addTablePrefix("sys_", "c_") // 设置过滤表前缀
                    //controller层配置
                    .controllerBuilder()
                    .enableRestStyle()/*开启生成@RestController 控制器*/
                    .enableHyphenStyle()//开启下划线转驼峰
                    //entity层配置
                    .entityBuilder()
                    .enableLombok();/*配置entity层使用Lombok*/
        });
        //模板配置
        generator.templateConfig(builder -> {
            builder.controller("/templates/controller.java.vm");//controller层的代码模板
        });
        //模板引擎配置可以使用Freemarker引擎模板，默认的是Velocity引擎模板（模板后缀名vm）
        generator.templateEngine(new VelocityTemplateEngine());
        generator.execute();
    }
}
