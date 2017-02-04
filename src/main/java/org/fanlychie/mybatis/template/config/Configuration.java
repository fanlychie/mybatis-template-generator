package org.fanlychie.mybatis.template.config;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.fanlychie.mybatis.template.config.Output.Item;
import org.fanlychie.mybatis.template.exception.ParseXmlException;
import org.fanlychie.mybatis.template.exception.RuntimeCastException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 配置
 * Created by fanlychie on 2017/1/30.
 */
public class Configuration {

    /**
     * 表配置
     */
    private static Table table;

    /**
     * 列配置
     */
    private static Column column;

    /**
     * 输出配置
     */
    private static Output output;

    /**
     * 扫描配置
     */
    private static Scanner scanner;

    /**
     * 数据源配置
     */
    private static Datasource datasource;

    /**
     * 属性配置
     */
    private static Map<String, String> properties;

    /**
     * 模板对照表
     */
    private static MouldMapping mouldMapping;

    static {
        try {
            // 载入文档
            Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("mybatis-template-generator.xml"));
            // 解析 properties 节点
            parsePropertiesNode(document);
            // 解析 datasource 节点
            parseDatasourceNode(document);
            // 解析 table 节点
            parseTableNode(document);
            // 解析 column 节点
            parseColumnNode(document);
            // 解析 scanner 节点
            parseScannerNode(document);
            // 解析 output 节点
            parseOutputNode(document);
        } catch (Throwable e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 获取表配置
     *
     * @return 返回表配置
     */
    public static Table getTable() {
        return table;
    }

    /**
     * 获取列配置
     *
     * @return 返回列配置
     */
    public static Column getColumn() {
        return column;
    }

    /**
     * 获取输出配置
     *
     * @return 返回输出配置
     */
    public static Output getOutput() {
        return output;
    }

    /**
     * 获取扫描配置
     *
     * @return 返回扫描配置
     */
    public static Scanner getScanner() {
        return scanner;
    }

    /**
     * 获取数据源配置
     *
     * @return 返回数据源配置
     */
    public static Datasource getDatasource() {
        return datasource;
    }

    /**
     * 获取属性配置
     *
     * @return 返回属性配置
     */
    public static Map<String, String> getProperties() {
        return properties;
    }

    /**
     * 获取模板对照表
     *
     * @return 返回模板对照表
     */
    public static MouldMapping getMouldMapping() {
        return mouldMapping;
    }

    /**
     * 模板对照表
     */
    public static class MouldMapping {

        /**
         * 对照表
         */
        private Map<Class<?>, String> mapping;

        /**
         * 创建一个模板对象
         *
         * @param scanner 扫描器对象
         */
        private MouldMapping(Scanner scanner) {
            init(scanner);
        }

        /**
         * 获取模板对照表
         *
         * @return 返回模板对照表
         */
        public Map<Class<?>, String> getMapping() {
            return mapping;
        }

        /**
         * 初始化
         *
         * @param scanner 扫描器对象
         */
        private void init(Scanner scanner) {
            mapping = new HashMap<>();
            String templatePathname = scanner.getTemplate().replace(".", "/");
            String velocityPathname = "/" + scanner.getVelocity().replace(".", "/") + "/";
            URL url = Thread.currentThread().getContextClassLoader().getResource(templatePathname);
            if ("org.fanlychie.mybatis.template.mould".equals(scanner.getTemplate())) {
                scanJarResources(url, templatePathname, velocityPathname);
            } else {
                scanUsrResources(url, velocityPathname);
            }
        }

        /**
         * 从 JAR 包扫描资源
         *
         * @param url              URL 链接地址
         * @param templatePathname 模板类路径
         * @param velocityPathname 模板文件路径
         */
        private void scanJarResources(URL url, String templatePathname, String velocityPathname) {
            JarURLConnection conn = null;
            try {
                conn = (JarURLConnection) url.openConnection();
            } catch (IOException e) {
                throw new RuntimeCastException(e);
            }
            Enumeration<JarEntry> es = null;
            try {
                es = conn.getJarFile().entries();
            } catch (IOException e) {
                throw new RuntimeCastException(e);
            }
            while (es.hasMoreElements()) {
                String name = es.nextElement().getName();
                if (name.startsWith(templatePathname) && name.endsWith(".class")) {
                    name = name.substring(0, name.indexOf(".class"));
                    name = name.replace("/", ".");
                    Class<?> templateClass = null;
                    try {
                        templateClass = Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        throw new ParseXmlException("找不到类 \"" + name + "\"");
                    }
                    mapping.put(templateClass, velocityPathname + templateClass.getSimpleName() + ".vm");
                }
            }
        }

        /**
         * 从用户自定义包扫描资源
         *
         * @param url              URL 链接地址
         * @param velocityPathname 模板文件路径
         */
        private void scanUsrResources(URL url, String velocityPathname) {
            if (url == null) {
                throw new ParseXmlException("找不到路径 \"" + scanner.getTemplate() + "\"");
            }
            File packageDir = new File(url.getPath());
            if (!packageDir.isDirectory()) {
                throw new ParseXmlException("找不到包路径 \"" + scanner.getTemplate() + "\"");
            }
            File[] files = packageDir.listFiles();
            if (files == null || files.length == 0) {
                throw new ParseXmlException("找不到包路径 \"" + scanner.getTemplate() + "\" 是空的");
            }
            for (File file : files) {
                if (!file.isDirectory()) {
                    String fileName = file.getName();
                    String name = scanner.getTemplate() + "." + fileName.substring(0, fileName.lastIndexOf("."));
                    Class<?> templateClass = null;
                    try {
                        templateClass = Class.forName(name);
                    } catch (ClassNotFoundException e) {
                        throw new ParseXmlException("找不到类 \"" + name + "\"");
                    }
                    mapping.put(templateClass, velocityPathname + templateClass.getSimpleName() + ".vm");
                }
            }
        }

    }

    /**
     * 解析 properties 节点
     *
     * @param document 文档对象
     * @throws Exception
     */
    private static void parsePropertiesNode(Document document) throws Exception {
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<Element> es = document.selectNodes("//properties/include");
        for (Element e : es) {
            String location = getElementAttributeValue(e, "location");
            try (InputStream in = classLoader.getResourceAsStream(location)) {
                props.load(in);
            } catch (NullPointerException ex) {
                throw new ParseXmlException("在类路径中找不到文件 \"" + location + "\"");
            }
        }
        properties = new HashMap(props);
        es = document.selectNodes("//properties/property");
        for (Element e : es) {
            properties.put(e.attributeValue("name"), e.attributeValue("value"));
        }
        properties.put("basedir", new File("").getAbsolutePath());
    }

    /**
     * 解析 datasource 节点
     *
     * @param document 文档对象
     * @throws Exception
     */
    private static void parseDatasourceNode(Document document) throws Exception {
        String root = "datasource";
        datasource = new Datasource();
        datasource.setUrl(getPropertyValue(document, root, "url"));
        datasource.setUsername(getPropertyValue(document, root, "username"));
        datasource.setPassword(getPropertyValue(document, root, "password"));
        datasource.setDriverClass(getPropertyValue(document, root, "driverClass"));
        datasource.setMetadataClass(getPropertyValue(document, root, "metadataClass", "org.fanlychie.mybatis.template.db.MySQLMetadata"));
    }

    /**
     * 解析 table 节点
     *
     * @param document 文档对象
     * @throws Exception
     */
    private static void parseTableNode(Document document) throws Exception {
        String root = "table";
        table = new Table();
        table.setSeparator(getPropertyValue(document, root, "separator", "_"));
        table.setIgnores(getPropertyValues(document, root, "ignores"));
        table.setEscapes(getPropertyValues(document, root, "escapes"));
        table.setPrefixs(getPropertyValues(document, root, "prefixs"));
    }

    /**
     * 解析 column 节点
     *
     * @param document 文档对象
     * @throws Exception
     */
    private static void parseColumnNode(Document document) throws Exception {
        String root = "column";
        column = new Column();
        column.setSeparator(getPropertyValue(document, root, "separator", "_"));
        column.setIgnores(getPropertyValues(document, root, "ignores"));
        column.setEscapes(getPropertyValues(document, root, "escapes"));
        column.setPrefixs(getPropertyValues(document, root, "prefixs"));
        Map<String, String> typeMapping = new HashMap<>();
        Element e = getPropertyElement(document, root, "typeMapping");
        if (e != null) {
            List<Element> es = e.selectNodes("./value");
            for (Element element : es) {
                typeMapping.put(getElementAttributeValue(element, "jdbcType"), getElementAttributeValue(element, "javaType"));
            }
        }
        column.setTypeMapping(typeMapping);
    }

    /**
     * 解析 scanner 节点
     *
     * @param document 文档对象
     * @throws Exception
     */
    private static void parseScannerNode(Document document) throws Exception {
        String root = "scanner";
        scanner = new Scanner();
        scanner.setTemplate(getPropertyValue(document, root, "template", "org.fanlychie.mybatis.template.mould"));
        scanner.setVelocity(getPropertyValue(document, root, "velocity", "org.fanlychie.mybatis.template.mould.vm"));
        mouldMapping = new MouldMapping(scanner);
    }

    /**
     * 解析 output 节点
     *
     * @param document 文档对象
     * @throws Exception
     */
    private static void parseOutputNode(Document document) throws Exception {
        output = new Output();
        output.setEntity(parseOutputItem(document, "entity"));
        output.setDao(parseOutputItem(document, "dao"));
        output.setDaoImpl(parseOutputItem(document, "daoImpl"));
        output.setMapperXml(parseOutputItem(document, "mapperXml"));
        output.setService(parseOutputItem(document, "service"));
        output.setServiceImpl(parseOutputItem(document, "serviceImpl"));
        output.setOverwrite(getPropertyValues(document, "output", "overwrite"));
    }

    /**
     * 解析 output 节点属性
     *
     * @param document  文档对象
     * @param attribute 属性名称
     * @return
     */
    private static Item parseOutputItem(Document document, String attribute) {
        Element e = getPropertyElement(document, "output", attribute);
        return new Item(getElementAttributeValue(e, "folder"), getElementAttributeValue(e, "package"));
    }

    /**
     * 获取属性的值
     *
     * @param document  文档对象
     * @param root      根节点对象
     * @param attribute 属性名称
     * @return 返回属性的值
     */
    private static String getPropertyValue(Document document, String root, String attribute) {
        return getPropertyValue(document, root, attribute, null);
    }

    /**
     * 获取属性的值
     *
     * @param document     文档对象
     * @param root         根节点对象
     * @param attribute    属性名称
     * @param defaultValue 默认值
     * @return 返回属性的值
     */
    private static String getPropertyValue(Document document, String root, String attribute, String defaultValue) {
        String value = getElementAttributeValue(getPropertyElement(document, root, attribute), "value");
        return value == null ? defaultValue : value;
    }

    /**
     * 获取属性的值集合
     *
     * @param document  文档对象
     * @param root      根节点对象
     * @param attribute 属性名称
     * @return 返回属性的值集合
     */
    private static Set<String> getPropertyValues(Document document, String root, String attribute) {
        Set<String> values = new HashSet<>();
        Element e = getPropertyElement(document, root, attribute);
        List<Element> es = e.selectNodes("./value");
        for (Element element : es) {
            values.add(getElementAttributeValue(element, null));
        }
        return values;
    }

    /**
     * 获取属性元素对象
     *
     * @param document  文档对象
     * @param root      根节点对象
     * @param attribute 属性名称
     * @return 返回属性元素对象
     */
    private static Element getPropertyElement(Document document, String root, String attribute) {
        return getElement(document, String.format("//%s/property[@name='%s']", root, attribute));
    }

    /**
     * 获取元素对象
     *
     * @param document 文档对象
     * @param xpath    路径
     * @return 返回元素对象
     */
    private static Element getElement(Document document, String xpath) {
        return (Element) document.selectSingleNode(xpath);
    }

    /**
     * 获取元素属性的值
     *
     * @param e         元素对象
     * @param attribute 属性名称
     * @return 返回元素属性的值
     */
    private static String getElementAttributeValue(Element e, String attribute) {
        if (e == null) {
            return null;
        }
        String value = attribute == null ? e.getStringValue() : e.attributeValue(attribute);
        // 解析 ${} 占位符
        Matcher matcher = Pattern.compile("\\$\\{[a-z_A-Z.1-9]+\\}").matcher(value);
        while (matcher.find()) {
            String placeholder = matcher.group();
            String val = properties.get(placeholder.substring(2, placeholder.length() - 1));
            value = value.replace(placeholder, val);
        }
        // 处理 * 星配符
        if (value.contains("*")) {
            value = value.replaceAll("\\*", ".*?");
        }
        return value;
    }

}