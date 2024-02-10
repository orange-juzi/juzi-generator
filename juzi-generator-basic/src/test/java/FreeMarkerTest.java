import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        // 创建Configuration对象，参数为FreeMarker版本号
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //指定模版文件的所在路径
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        //设置模版文件使用的字符集
        cfg.setDefaultEncoding("UTF-8");
        //生成没有逗号的数字格式
        cfg.setNumberFormat("0.######");
        //创建模版对象，加载指定模版
        Template template = cfg.getTemplate("myweb.html.ftl");
        //数据模型
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("currentYear", 2024);

        List<Map<String, Object>> menuItems = new ArrayList<>();

        Map<String, Object> menuItem1 = new HashMap<>();
        menuItem1.put("url", "https://baidu.com");
        menuItem1.put("label", "橘子生成器1");

        Map<String, Object> menuItem2 = new HashMap<>();
        menuItem2.put("url", "https://taobao.com");
        menuItem2.put("label", "橘子生成器2");

        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        dataModel.put("menuItems", menuItems);

        Writer out = new FileWriter("myweb.html");
        template.process(dataModel, out);
        out.close();
    }
}
