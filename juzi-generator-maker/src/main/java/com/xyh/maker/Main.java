package com.xyh.maker;

//import com.xyh.maker.cli.CommandExecutor;


import com.xyh.maker.generator.main.GenerateTemplate;
import com.xyh.maker.generator.main.MainGenerator;
import com.xyh.maker.generator.main.ZipGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
//        GenerateTemplate generateTemplate = new MainGenerator();
        GenerateTemplate generateTemplate = new ZipGenerator();
        generateTemplate.doGenerate();
    }
}