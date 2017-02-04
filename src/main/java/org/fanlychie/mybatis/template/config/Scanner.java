package org.fanlychie.mybatis.template.config;

/**
 * 扫描配置
 * Created by fanlychie on 2017/1/30.
 */
public class Scanner {

    /**
     * Java 模板路径
     */
    private String template;

    /**
     * Velocity 模板路径
     */
    private String velocity;

    /**
     * 获取 Java 模板路径
     *
     * @return 返回 Java 模板路径
     */
    public String getTemplate() {
        return template;
    }

    /**
     * 获取 Velocity 模板路径
     *
     * @return 返回 Velocity 模板路径
     */
    public String getVelocity() {
        return velocity;
    }

    /**
     * 设置 Java 模板路径
     *
     * @param template Java 模板路径
     */
    void setTemplate(String template) {
        this.template = template;
    }

    /**
     * 设置 Velocity 模板路径
     *
     * @param velocity Velocity 模板路径
     */
    void setVelocity(String velocity) {
        this.velocity = velocity;
    }

}