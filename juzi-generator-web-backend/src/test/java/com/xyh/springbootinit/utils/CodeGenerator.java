package com.xyh.springbootinit.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class CodeGenerator {

    @Test
    void contextLoads() {
        // 定义数据表名称
        String tableName = "generator";
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/generator_db?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false", "root", "1111")
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("juzi") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定输出目录
                })
                // 包名配置
                .packageConfig(builder -> {
                    builder.parent("com.xyh") // 设置父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
                            .entity("entity") // 实体类包名
                            .controller("controller")// 控制层包名
                            .mapper("mapper")// mapper层包名
                            .service("service") // service层包名
                            .serviceImpl("service.impl") // service实现类包名
                            .moduleName("springbootinit") // 设置父包模块名
                            // 自定义mapper.xml文件输出目录
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir") +
                                    "/src/main/resources/mapper"));
                })
                // 策略设置
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok()
                            .idType(IdType.ASSIGN_ID)
                            .mapperBuilder()
                            .enableBaseResultMap();//启用xml文件中的BaseResultMap 生成
                    builder.controllerBuilder().enableRestStyle(); //开启生成@RestController 控制器
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix(""); // 设置过滤表前缀
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService") // 格式化 service 接口文件名称
                            .formatServiceImplFileName("%sServiceImpl"); // 格式化 service 接口文件名称
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
