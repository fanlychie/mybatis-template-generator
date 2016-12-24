package org.fanlychie.mybatis.core;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * 模板引擎
 * 
 * @author fanlychie
 */
public class TemplateEngine {

	/**
	 * 解析模板
	 * 
	 * @param template
	 *            模板文件名称
	 * @param contextParams
	 *            模板上下文所需的参数
	 * @return
	 */
	public String parseTemplate(String template, Map<String, Object> contextParams) throws Throwable {
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		thread.setContextClassLoader(this.getClass().getClassLoader());
		try (StringWriter writer = new StringWriter()) {
			VelocityEngine engine = getVelocityEngine();
			engine.getTemplate(template).merge(buildContext(contextParams), writer);
			return writer.toString();
		} finally {
			thread.setContextClassLoader(loader);
		}
	}

	/**
	 * 构建模板上下文
	 * 
	 * @param contextParams
	 *            模板上下文所需的参数
	 * @return VelocityContext
	 */
	private VelocityContext buildContext(Map<String, Object> contextParams) {
		VelocityContext context = new VelocityContext();
		for (String key : contextParams.keySet()) {
			context.put(key, contextParams.get(key));
		}
		return context;
	}
	
	/**
	 * 获取 Velocity 引擎对象
	 * 
	 * @return
	 * @throws Throwable
	 */
	private VelocityEngine getVelocityEngine() throws Throwable {
		Properties prop = new Properties();
		prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		prop.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
		prop.load(ClassLoader.getSystemResourceAsStream("velocity.properites"));
		prop.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		VelocityEngine engine = new VelocityEngine();
		engine.init(prop);
		return engine;
	}
	
}