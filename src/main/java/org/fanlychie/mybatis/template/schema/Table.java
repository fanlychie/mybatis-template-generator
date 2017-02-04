package org.fanlychie.mybatis.template.schema;

import java.util.List;

/**
 * 表
 * Created by fanlychie on 2017/1/30.
 */
public class Table {

    /**
     * 表映射到对象的名称
     */
    private String name;

    /**
     * 表在数据库中的名称
     */
    private String origin;

    /**
     * 注释
     */
    private String comment;

    /**
     * 表的主键
     */
    private Column primaryKey;

    /**
     * 除主键外, 表的列集合
     */
    private List<Column> columns;

    /**
     * 获取表的对象名称
     *
     * @return 表的对象名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置表的对象名称
     *
     * @param name 表的对象名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取表的数据库名称
     *
     * @return 表的数据库名称
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * 设置表的数据库名称
     *
     * @param origin 表的数据库名称
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * 获取表的备注
     *
     * @return 表的备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置表的备注
     *
     * @param comment 表的备注
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取表的主键
     *
     * @return 表的主键
     */
    public Column getPrimaryKey() {
        return primaryKey;
    }

    /**
     * 设置表的主键
     *
     * @param primaryKey 表的主键
     */
    public void setPrimaryKey(Column primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * 获取表的列集合
     *
     * @return 表的列集合
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     * 设置表的列集合
     *
     * @param columns 表的列集合
     */
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

}