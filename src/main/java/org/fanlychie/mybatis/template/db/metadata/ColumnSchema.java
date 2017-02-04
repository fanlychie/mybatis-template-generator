package org.fanlychie.mybatis.template.db.metadata;

/**
 * 列模式
 * Created by fanlychie on 2017/2/1.
 */
public class ColumnSchema {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 注释
     */
    private String comment;

    /**
     * 是否是主键
     */
    private boolean primaryKey;

    /**
     * 创建一个列模式对象
     *
     * @param name       列名称
     * @param type       列类型
     * @param comment    列注释
     * @param primaryKey 是否是主键
     */
    public ColumnSchema(String name, String type, String comment, boolean primaryKey) {
        this.name = name;
        this.type = type;
        this.comment = comment;
        this.primaryKey = primaryKey;
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
     * 获取类型
     *
     * @return 返回类型
     */
    public String getType() {
        return type;
    }

    /**
     * 获取注释
     *
     * @return 返回注释
     */
    public String getComment() {
        return comment;
    }

    /**
     * 是否是主键
     *
     * @return 主键返回 true, 否则返回 false
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

}