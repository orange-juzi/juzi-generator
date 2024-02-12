package com.xyh.maker.generator.file;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 动态文件生成
 */
public class DynamicFileGenerator {

    /**
     * 生成文件
     *
     * @param inputPath  模版文件输入路径
     * @param outputPath 输出路径
     * @param model      数据模型
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

        //文件不存在则创建文件和父目录
        if (!FileUtil.exist(outputPath)) {
            FileUtil.touch(outputPath);
        }

        //生成
        Writer out = new FileWriter(outputPath);
        template.process(model, out);

        //生成文件需要关闭
        out.close();
    }

}
