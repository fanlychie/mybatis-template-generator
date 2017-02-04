package org.fanlychie.mybatis.template.engine;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.fanlychie.mybatis.template.exception.RuntimeCastException;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * 模板引擎
 * Created by fanlychie on 2017/1/30.
 */
public class TemplateEngine {

    /**
     * 引擎
     */
    private static VelocityEngine engine;

    /**
     * 解析模板
     *
     * @param pathname 模板文件路径名称
     * @param params   自定义的模板参数
     * @return 返回模板文件的解析内容
     */
    public static String parseTemplate(String pathname, Map<String, Object> params) throws Throwable {
        try (StringWriter writer = new StringWriter()) {
            engine.getTemplate(pathname).merge(new VelocityContext(params), writer);
            return writer.toString();
        }
    }

    /**
     * 初始化模板引擎
     */
    private static void init() {
        try {
            Properties prop = new Properties();
            prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
            prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
            prop.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
            prop.load(TemplateEngine.class.getResourceAsStream("/velocity.properites"));
            prop.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            engine = new VelocityEngine();
            engine.init(prop);
        } catch (Throwable e) {
            throw new RuntimeCastException(e);
        }
    }

    static { init(); }

}