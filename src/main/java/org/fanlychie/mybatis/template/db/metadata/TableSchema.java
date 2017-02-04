package org.fanlychie.mybatis.template.db.metadata;

/**
 * 表模式
 * Created by fanlychie on 2017/2/1.
 */
public class TableSchema {

    /**
     * 名称
     */
    private String name;

    /**
     * 注释
     */
    private String comment;

    /**
     * 创建一个表模式对象
     *
     * @param name    表名称
     * @param comment 表注释
     */
    public TableSchema(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    /**
     * 获取名称
     *
     * @return 返回名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取注释
     *
     * @return 返回注释
     */
    public String getComment() {
        return comment;
    }

}