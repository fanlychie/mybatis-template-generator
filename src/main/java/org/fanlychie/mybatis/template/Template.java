package org.fanlychie.mybatis.template;

import java.util.Map;

/**
 * 模板接口
 *
 * @author fanlychie
 */
public interface Template {

    /**
     * 设置模板文件上下文的参数
     *
     * @param params 参数
     */
    void setContextParams(Map<String, Object> params);

    /**
     * 模板文件输出的路径
     *
     * @param tableName 表名称
     * @return 返回模板文件输出的路径
     */
    String getFileOutputPath(String tableName);

    /**
     * 是否为多文件模式, 多文件模式会为每一个表对应生成一个模板文件
     *
     * @return 多文件模式返回 true, 否则返回 false
     */
    boolean isMultiFileMode();

    /**
     * 是否覆盖输出文件
     *
     * @return 覆盖输出返回 true, 存在不输出则返回 false
     */
    boolean isOverride();

}