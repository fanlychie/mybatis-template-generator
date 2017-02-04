package org.fanlychie.mybatis.template.config;

import java.util.Set;

/**
 * 表配置
 * Created by fanlychie on 2017/1/30.
 */
public class Table {

    /**
     * 分隔符
     */
    private String separator;

    /**
     * 忽略的表
     */
    private Set<String> ignores;

    /**
     * 逃逸的表
     */
    private Set<String> escapes;

    /**
     * 表前缀的名称
     */
    private Set<String> prefixs;

    /**
     * 获取分隔符
     *
     * @return 返回分隔符
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * 获取忽略的表
     *
     * @return 返回忽略的表
     */
    public Set<String> getIgnores() {
        return ignores;
    }

    /**
     * 获取逃逸的表
     *
     * @return 返回逃逸的表
     */
    public Set<String> getEscapes() {
        return escapes;
    }

    /**
     * 获取表前缀的名称
     *
     * @return 返回表前缀的名称
     */
    public Set<String> getPrefixs() {
        return prefixs;
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
     * 设置忽略的表
     *
     * @param ignores 忽略的表
     */
    void setIgnores(Set<String> ignores) {
        this.ignores = ignores;
    }

    /**
     * 设置逃逸的表
     *
     * @param escapes 逃逸的表
     */
    void setEscapes(Set<String> escapes) {
        this.escapes = escapes;
    }

    /**
     * 设置表前缀的名称
     *
     * @param prefixs 表前缀的名称
     */
    void setPrefixs(Set<String> prefixs) {
        this.prefixs = prefixs;
    }

}