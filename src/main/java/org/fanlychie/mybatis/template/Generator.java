package org.fanlychie.mybatis.template;

import org.fanlychie.mybatis.template.config.Configuration;
import org.fanlychie.mybatis.template.context.Util;
import org.fanlychie.mybatis.template.db.DatabaseMetadata;
import org.fanlychie.mybatis.template.engine.TemplateEngine;
import org.fanlychie.mybatis.template.exception.RuntimeCastException;
import org.fanlychie.mybatis.template.schema.Column;
import org.fanlychie.mybatis.template.schema.Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 模板生成器
 * Created by fanlychie on 2017/2/4.
 */
public final class Generator {

    /**
     * 私有化
     */
    private Generator() {

    }

    /**
     * 生成模板
     */
    public static void generate() {
        try {
            System.out.println("\n[INFO] ------------------------------------------------------------------------");
            System.out.println("[INFO] mybatis-template-generator 2.0");
            System.out.println("[INFO] ------------------------------------------------------------------------\n");
            String metadataClass = Configuration.getDatasource().getMetadataClass();
            DatabaseMetadata metadata = (DatabaseMetadata) Class.forName(metadataClass).newInstance();
            List<Table> tables = metadata.getTables();
            Map<String, Object> contextParams = new HashMap<>(Configuration.getProperties());
            contextParams.put("util", new Util());
            Map<Class<?>, String> mapping = Configuration.getMouldMapping().getMapping();
            for (Class<?> templateClass : mapping.keySet()) {
                Template template = (Template) templateClass.newInstance();
                if (template.isMultiFileMode()) {
                    produceMultiFileTemplate(tables, template, mapping.get(templateClass), contextParams);
                } else {
                    produceSingleFileTemplate(tables, template, mapping.get(templateClass), contextParams);
                }
            }
            System.out.println("[INFO] ------------------------------------------------------------------------");
            System.out.println("[INFO] GENERATE SUCCESS");
            System.out.println("[INFO] ------------------------------------------------------------------------");
        } catch (Throwable e) {
            throw new RuntimeCastException(e);
        }
    }

    /**
     * 产生多文件模式的模板文件
     *
     * @param tables   表集合
     * @param template 模板对象
     * @param pathname 模板文件路径
     * @param params   上下文参数
     * @throws Throwable
     */
    private static void produceMultiFileTemplate(List<Table> tables, Template template, String pathname, Map<String, Object> params) throws Throwable {
        for (Table table : tables) {
            params.put("table", table);
            LinkedList<Column> allColumns = new LinkedList<>(table.getColumns());
            allColumns.add(0, table.getPrimaryKey());
            params.put("_columns", allColumns);
            produceTemplateFile(table.getName(), template, pathname, params);
        }
    }

    /**
     * 产生单文件模式的模板文件
     *
     * @param tables   表集合
     * @param template 模板对象
     * @param pathname 模板文件路径
     * @param params   上下文参数
     * @throws Throwable
     */
    private static void produceSingleFileTemplate(List<Table> tables, Template template, String pathname, Map<String, Object> params) throws Throwable {
        params.put("tables", tables);
        produceTemplateFile(null, template, pathname, params);
    }

    /**
     * 产生模板文件
     *
     * @param tableName 表名称
     * @param template  模板对象
     * @param pathname  模板文件路径
     * @param params    上下文参数
     * @throws Throwable
     */
    private static void produceTemplateFile(String tableName, Template template, String pathname, Map<String, Object> params) throws Throwable {
        File file = new File(template.getFileOutputPath(tableName));
        if (isWritable(template, file)) {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            template.setContextParams(params);
            produce(file, TemplateEngine.parseTemplate(pathname, params));
        }
    }

    /**
     * 产生文件
     *
     * @param dest    目标文件
     * @param content 文件内容
     * @throws Throwable
     */
    private static void produce(File dest, String content) throws Throwable {
        try (BufferedReader reader = new BufferedReader(new StringReader(content));
             BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {
            int read;
            char[] buffer = new char[1024 * 1024 / 2];
            while ((read = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, read);
            }
            printDetail(dest);
        }
    }

    /**
     * 输出文件消息
     *
     * @param dest 目标文件
     */
    private static void printDetail(File dest) {
        String pathname = dest.getAbsolutePath();
        pathname = pathname.replace("\\", "/");
        while (pathname.indexOf("/..") != -1) {
            int index = pathname.indexOf("../");
            String lpath = pathname.substring(0, index - 1);
            String rpath = pathname.substring(index + 2);
            pathname = lpath.substring(0, lpath.lastIndexOf("/")) + rpath;
        }
        pathname = pathname.replace("/.", "");
        String info = String.format("%s %s %s", "[C]", pathname, "\n");
        System.out.println(info);
    }

    /**
     * 判断是否可以写出文件
     *
     * @param template 模板类
     * @param file     目标文件
     * @return 可以写出返回 true, 否则返回 false
     */
    private static boolean isWritable(Template template, File file) {
        if (template.isOverride() || !file.exists()) {
            return true;
        }
        String fileName = file.getName();
        for (String pattern : Configuration.getOutput().getOverwrite()) {
            if (fileName.matches(pattern)) {
                return true;
            }
        }
        return false;
    }

}