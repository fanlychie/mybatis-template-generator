package org.fanlychie.mybatis.template.config;

import java.util.Map;
import java.util.Set;

/**
 * 列配置
 * Created by fanlychie on 2017/1/30.
 */
public class Column {

    /**
     * 分隔符
     */
    private String separator;

    /**
     * 忽略的列
     */
    private Set<String> ignores;

    /**
     * 逃逸的列
     */
    private Set<String> escapes;

    /**
     * 列前缀的名称
     */
    private Set<String> prefixs;

    /**
     * 类型映射
     */
    private Map<String, String> typeMapping;

    /**
     * 获取分隔符
     *
     * @return 返回分隔符
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * 获取忽略的列
     *
     * @return 返回忽略的列
     */
    public Set<String> getIgnores() {
        return ignores;
    }

    /**
     * 获取逃逸的列
     *
     * @return 返回逃逸的列
     */
    public Set<String> getEscapes() {
        return escapes;
    }

    /**
     * 获取列前缀的名称
     *
     * @return 返回列前缀的名称
     */
    public Set<String> getPrefixs() {
        return prefixs;
    }

    /**
     * 获取类型映射
     *
     * @return 返回类型映射
     */
    public Map<String, String> getTypeMapping() {
        return typeMapping;
    }

    /**
     * 设置分隔符
     *
     * @param separator 分隔符
     */
    void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * 设置忽略的列
     *
     * @param ignores 忽略的列
     */
    void setIgnores(Set<String> ignores) {
        this.ignores = ignores;
    }

    /**
     * 设置逃逸的列
     *
     * @param escapes 逃逸的列
     */
    void setEscapes(Set<String> escapes) {
        this.escapes = escapes;
    }

    /**
     * 设置列前缀的名称
     *
     * @param prefixs 列前缀的名称
     */
    void setPrefixs(Set<String> prefixs) {
        this.prefixs = prefixs;
    }

    /**
     * 设置类型映射
     *
     * @param typeMapping 类型映射
     */
    void setTypeMapping(Map<String, String> typeMapping) {
        this.typeMapping = typeMapping;
    }

}