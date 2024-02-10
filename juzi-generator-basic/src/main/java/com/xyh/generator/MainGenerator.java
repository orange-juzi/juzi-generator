package com.xyh.generator;

import com.xyh.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 核心生成器
 */
public class MainGenerator {

    public static void doGenerate(Object model) throws TemplateException, IOException {
        String projectPath = System.getProperty("user.dir");
        //整个项目的根路径
        File parentFile = new File(projectPath).getParentFile();
        String inputPath = new File(parentFile, "juzi-generator-demo-projects/acm-template").getAbsolutePath();
        String outputPath = projectPath;
        //生成静态文件
        StaticGenerator.copyFiles(inputPath, outputPath);
        //生成动态文件
        String inputDynamicFilePath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String outputDynamicFilePath = outputPath + File.separator + "acm-template/src/com/xyh/acm/MainTemplate.java";
        DynamicGenerator.doGenerate(inputDynamicFilePath, outputDynamicFilePath, model);
    }

    public static void main(String[] args) throws TemplateException, IOException {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("juzi");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        doGenerate(mainTemplateConfig);
    }
}
