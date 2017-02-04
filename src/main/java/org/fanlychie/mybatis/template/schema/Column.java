package org.fanlychie.mybatis.template.schema;

/**
 * 列
 * Created by fanlychie on 2017/1/30.
 */
public class Column {

    /**
     * 列映射到对象的名称
     */
    private String name;

    /**
     * 列在数据库中的名称
     */
    private String origin;

    /**
     * 类型
     */
    private String type;

    /**
     * 注释
     */
    private String comment;

    /**
     * 获取列的对象名称
     *
     * @return 返回列的对象名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置列的对象名称
     *
     * @param name 列的对象名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取列的数据库名称
     *
     * @return 列的数据库名称
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * 设置列的数据库名称
     *
     * @param origin 列的数据库名称
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * 获取列的类型
     *
     * @return 列的类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置列的类型
     *
     * @param type 列的类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取列的备注
     *
     * @return 列的备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置列的备注
     *
     * @param comment 列的备注
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

}