package com.xyh.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.xyh.maker.generator.JarGenerator;
import com.xyh.maker.generator.ScriptGenerator;
import com.xyh.maker.generator.file.DynamicFileGenerator;
import com.xyh.maker.meta.Meta;
import com.xyh.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 生成代码生成器
 */
public class MainGenerator extends GenerateTemplate {

    @Override
    protected void buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        System.out.println("不要给我输出 dist 啦！");
    }
}
