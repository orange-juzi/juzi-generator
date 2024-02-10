package com.xyh.generator;

import com.xyh.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态文件生成
 */
public class DynamicGenerator {
    public static void main(String[] args) throws IOException, TemplateException {
        String projectPath = System.getProperty("user.dir") + File.separator;
        String inputPath = projectPath  + "src/main/resources/templates/MainTemplate.java.ftl";
        String outputPath = projectPath + "MainTemplate.java";
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("juzi");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        doGenerate(inputPath, outputPath, mainTemplateConfig);

    }

    /**
     * 生成文件
     * @param inputPath 模版文件输入路径
     * @param outputPath 输出路径
     * @param model 数据模型
     * @throws IOException
     * @throws TemplateException
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // 创建Configuration对象，参数为FreeMarker版本号
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //指定模版文件的所在路径
        File templateDir = new File(inputPath).getParentFile();
        cfg.setDirectoryForTemplateLoading(templateDir);
        //设置模版文件使用的字符集
        cfg.setDefaultEncoding("UTF-8");

        //创建模版对象，加载指定模版
        String templateName = new File(inputPath).getName();
        Template template = cfg.getTemplate(templateName);

        //创建数据模型
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("juzi");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");

        //生成
        Writer out = new FileWriter(outputPath);
        template.process(model, out);

        //生成文件需要关闭
        out.close();
    }

}
