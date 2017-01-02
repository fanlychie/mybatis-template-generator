package org.fanlychie.mybatis;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.fanlychie.mybatis.schema.DataSource;
import org.fanlychie.mybatis.schema.Output;
import org.fanlychie.mybatis.schema.Output.Item;
import org.fanlychie.mybatis.schema.Scanner;
import org.fanlychie.mybatis.schema.Schema;
import org.fanlychie.mybatis.template.EntityTemplate;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XML 配置文件解析器
 * 
 * @author fanlychie
 */
@SuppressWarnings("unchecked")
public class ConfigXmlParser {

	private static Document document = openDocument();

	private static Map<String, Object> props = parseProperties();
	
	private static Output output = parseOutput();

	private static Document openDocument() {
		try {
			return new SAXReader().read(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("mybatis-template-generator.xml"));
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解析 datasource 节点
	 */
	public static DataSource parseDataSource() throws IOException {
		DataSource dataSource = new DataSource();
		Element e = (Element) document.selectSingleNode("//datasource/property[@name='url']");
		dataSource.setUrl(processVal(e.attributeValue("value")));
		e = (Element) document.selectSingleNode("//datasource/property[@name='username']");
		dataSource.setUsername(processVal(e.attributeValue("value")));
		e = (Element) document.selectSingleNode("//datasource/property[@name='password']");
		dataSource.setPassword(processVal(e.attributeValue("value")));
		e = (Element) document.selectSingleNode("//datasource/property[@name='driverClass']");
		dataSource.setDriverClass(processVal(e.attributeValue("value")));
		return dataSource;
	}

	/**
	 * 解析 schema 节点
	 */
	public static Schema parseSchema() throws IOException {
		Schema schema = new Schema();
		Element e = (Element) document.selectSingleNode("//schema/property[@name='tableNameSeparator']");
		schema.setTableNameSeparator(processVal(e.attributeValue("value")));
		e = (Element) document.selectSingleNode("//schema/property[@name='columnNameSeparator']");
		schema.setColumnNameSeparator(processVal(e.attributeValue("value")));
		e = (Element) document.selectSingleNode("//schema/property[@name='tableIgnores']");
		schema.setTableIgnores(processVal(e.attributeValue("value")));
		e = (Element) document.selectSingleNode("//schema/property[@name='tableEscapes']");
		Set<String> set = new HashSet<>();
		List<Element> es = e.selectNodes("./value");
		for (Element e1 : es) {
			set.add(processVal(e1.getStringValue()));
		}
		schema.setTableEscapes(set);
		e = (Element) document.selectSingleNode("//schema/property[@name='columnIgnores']");
		set = new HashSet<>();
		es = e.selectNodes("./value");
		for (Element e1 : es) {
			set.add(processVal(e1.getStringValue()));
		}
		schema.setColumnIgnores(set);
		e = (Element) document.selectSingleNode("//schema/property[@name='tablePrefix']");
		set = new HashSet<>();
		es = e.selectNodes("./value");
		for (Element e1 : es) {
			set.add(processVal(e1.getStringValue()));
		}
		schema.setTablePrefix(set);
		e = (Element) document.selectSingleNode("//schema/property[@name='dataTypes']");
		Map<String, String> map = new HashMap<>();
		es = e.selectNodes("./value");
		for (Element e1 : es) {
			map.put(e1.attributeValue("jdbcType"), e1.attributeValue("javaType"));
		}
		schema.setDataTypes(map);
		return schema;
	}

	/**
	 * 解析 scanner 节点
	 */
	public static Scanner parseScanner() throws IOException {
		Scanner scanner = new Scanner();
		Element e = (Element) document.selectSingleNode("//scanner/property[@name='templateClasses']");
		String packageName = e.attributeValue("value");
		String pathname = packageName.replace(".", "/");
		URL url = Thread.currentThread().getContextClassLoader().getResource(pathname);
		Set<String> fileSet = new HashSet<>();
		if (packageName.equals(EntityTemplate.class.getPackage().getName())) {
			JarURLConnection conn = (JarURLConnection) url.openConnection();
			Enumeration<JarEntry> es = conn.getJarFile().entries();
			while (es.hasMoreElements()) {
				String epathname = es.nextElement().getName();
				if (epathname.startsWith(pathname) && epathname.endsWith(".class")) {
					epathname = epathname.substring(0, epathname.indexOf(".class"));
					fileSet.add(epathname.replace("/", "."));
				}
			}
		} else {
			if (url == null) {
				throw new RuntimeException("can not found path : " + pathname);
			}
			File packageDir = new File(url.getPath());
			if (!packageDir.isDirectory()) {
				throw new RuntimeException(pathname + " is not a directory");
			}
			File[] files = packageDir.listFiles();
			if (files == null || files.length == 0) {
				throw new RuntimeException("the directory " + pathname + " is empty");
			}
			for (File file : files) {
				if (!file.isDirectory()) {
					String fileName = file.getName();
					fileSet.add(packageName + "." + fileName.substring(0, fileName.lastIndexOf(".")));
				}
			}
		}
		scanner.setTemplateClasses(fileSet);
		e = (Element) document.selectSingleNode("//scanner/property[@name='templateVmsPath']");
		scanner.setTemplateVmsPath("/" + processVal(e.attributeValue("value")).replace(".", "/") + "/");
		return scanner;
	}

	public static Output getOutput() {
		return output;
	}

	public static Map<String, Object> getProps() throws IOException {
		return props;
	}

	/**
	 * 解析 output 节点
	 */
	private static Output parseOutput() {
		Output output = new Output();
		Element e = (Element) document.selectSingleNode("//output/property[@name='entity']");
		output.setEntity(new Item(processBasedir(e.attributeValue("folder")), e.attributeValue("packname")));
		e = (Element) document.selectSingleNode("//output/property[@name='dao']");
		output.setDao(new Item(processBasedir(e.attributeValue("folder")), e.attributeValue("packname")));
		e = (Element) document.selectSingleNode("//output/property[@name='daoImpl']");
		output.setDaoImpl(new Item(processBasedir(e.attributeValue("folder")), e.attributeValue("packname")));
		e = (Element) document.selectSingleNode("//output/property[@name='mapperXml']");
		output.setMapperXml(new Item(processBasedir(e.attributeValue("folder")), e.attributeValue("packname")));
		e = (Element) document.selectSingleNode("//output/property[@name='service']");
		output.setService(new Item(processBasedir(e.attributeValue("folder")), e.attributeValue("packname")));
		e = (Element) document.selectSingleNode("//output/property[@name='serviceImpl']");
		output.setServiceImpl(new Item(processBasedir(e.attributeValue("folder")), e.attributeValue("packname")));
		return output;
	}

	/**
	 * 解析 properties 节点
	 */
	private static Map<String, Object> parseProperties() {
		Properties props = new Properties();
		// 解析 <properties location="xxx"> 节点
		List<Element> es = document.selectNodes("//properties[@location]");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		for (Element e : es) {
			try (InputStream in = loader.getResourceAsStream(e.attributeValue("location"))) {
				props.load(in);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
		@SuppressWarnings("rawtypes")
		Map map = new HashMap<>(props);
		// 解析 <properties> 节点的 <property> 子节点
		es = document.selectNodes("//properties/property");
		for (Element e : es) {
			map.put(e.attributeValue("name"), e.attributeValue("value"));
		}
		return map;
	}

	private static String processVal(String str) throws IOException {
		if (str.matches("\\$\\{.*?\\}")) {
			// 处理 ${} 占位符
			Matcher matcher = Pattern.compile("[a-z_A-Z.1-9]+").matcher(str);
			if (matcher.find()) {
				String placeholder = matcher.group();
				Object value = getProps().get(placeholder);
				return value == null ? null : value.toString();
			}
		}
		// 处理 * 星配符
		if (str.contains("*")) {
			return str.replaceAll("\\*", ".*?");
		}
		return str;
	}

	private static String processBasedir(String str) {
		return str.contains("${basedir}") ? str.replace("${basedir}", new File("").getAbsolutePath()) : str;
	}

}