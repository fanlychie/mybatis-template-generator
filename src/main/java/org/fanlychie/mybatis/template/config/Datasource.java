package org.fanlychie.mybatis.template.config;

/**
 * 数据源配置
 * Created by fanlychie on 2017/1/30.
 */
public class Datasource {

    /**
     * 数据库链接地址
     */
    private String url;

    /**
     * 账户名称
     */
    private String username;

    /**
     * 账户密码
     */
    private String password;

    /**
     * 数据库的驱动类
     */
    private String driverClass;

    /**
     * 元数据类
     */
    private String metadataClass;

    /**
     * 获取数据库链接地址
     *
     * @return 返回数据库链接地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取账户名称
     *
     * @return 返回账户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取账户密码
     *
     * @return 返回账户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取数据库的驱动类
     *
     * @return 返回数据库的驱动类
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * 获取元数据类
     *
     * @return 返回元数据类
     */
    public String getMetadataClass() {
        return metadataClass;
    }

    /**
     * 设置数据库链接地址
     *
     * @param url 数据库链接地址
     */
    void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置账户名称
     *
     * @param username 账户名称
     */
    void setUsername(String username) {
        this.username = username;
    }

    /**
     * 设置账户密码
     *
     * @param password 账户密码
     */
    void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置数据库的驱动类
     *
     * @param driverClass 数据库的驱动类
     */
    void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * 设置元数据类
     *
     * @param metadataClass 元数据类
     */
    void setMetadataClass(String metadataClass) {
        this.metadataClass = metadataClass;
    }

}