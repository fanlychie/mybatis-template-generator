package org.fanlychie.mybatis.template.context;

import org.fanlychie.mybatis.template.schema.Column;
import org.fanlychie.mybatis.template.schema.Table;

/**
 * 工具类
 * Created by fanlychie on 2017/2/1.
 */
public final class Util {

    /**
     * 判断对象为 null
     *
     * @param obj 对象
     * @return null 对象返回 true, 否则返回 false
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断对象不为 null
     *
     * @param obj 对象
     * @return null 对象返回 false, 否则返回 true
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断字符串为空
     *
     * @param str 字符串
     * @return 空字符串返回 true, 否则返回 false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断字符串不为空
     *
     * @param str 字符串
     * @return 空字符串返回 false, 否则返回 true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 字符串首字母大写
     *
     * @param str 字符串
     * @return 返回首字母大写的字符串
     */
    public static String capitalize(String str) {
        if (isNotEmpty(str)) {
            if (str.length() == 1) {
                return str.toUpperCase();
            }
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
        }
        return str;
    }

    /**
     * 方法前缀
     *
     * @param type 数据类型
     * @return boolean 类型的返回 is, 否则返回 get
     */
    public static String methodPrefix(String type) {
        return type.equalsIgnoreCase("boolean") ? "is" : "get";
    }

    /**
     * 获取表插入的列内容
     *
     * @param table 表对象
     * @return 返回表插入的列内容
     */
    public static String getTableInsertColumns(Table table) {
        StringBuilder builder = new StringBuilder();
        builder.append(table.getPrimaryKey().getOrigin().toUpperCase());
        for (Column column : table.getColumns()) {
            builder.append(", ").append(column.getOrigin().toUpperCase());
        }
        return builder.toString();
    }

    /**
     * 获取表插入的值内容
     *
     * @param table 表对象
     * @return 返回表插入的值内容
     */
    public static String getTableInsertValues(Table table) {
        StringBuilder builder = new StringBuilder();
        builder.append("#{").append(table.getPrimaryKey().getName()).append("}");
        for (Column column : table.getColumns()) {
            builder.append(", #{").append(column.getName()).append("}");
        }
        return builder.toString();
    }

}