package org.fanlychie.mybatis.template.config;

import java.util.Set;

/**
 * 输出配置
 * Created by fanlychie on 2017/1/30.
 */
public class Output {

    /**
     * 实体类
     */
    private Item entity;

    /**
     * DAO 接口
     */
    private Item dao;

    /**
     * DAO 实现类
     */
    private Item daoImpl;

    /**
     * 实体类对应的 Xml 文件
     */
    private Item mapperXml;

    /**
     * Service 接口
     */
    private Item service;

    /**
     * Service 实现类
     */
    private Item serviceImpl;

    /**
     * 强制重写输出的表
     */
    private Set<String> overwrite;

    /**
     * 项
     */
    public static class Item {

        /**
         * 文件夹
         */
        private String folder;

        /**
         * 包名
         */
        private String packname;

        /**
         * 创建一个项
         *
         * @param folder   文件夹
         * @param packname 包名
         */
        Item(String folder, String packname) {
            this.folder = folder;
            this.packname = packname;
        }

        /**
         * 获取文件夹
         *
         * @return 返回文件夹
         */
        public String getFolder() {
            return folder;
        }

        /**
         * 获取包名
         *
         * @return 返回包名
         */
        public String getPackname() {
            return packname;
        }

        @Override
        public String toString() {
            return convertSeparator(folder) + convertSeparator(packname);
        }

        /**
         * 转换分隔符
         *
         * @param source 源字符串
         * @return 返回文件分隔符的字符串
         */
        private String convertSeparator(String source) {
            if (source.contains(".")) {
                source = source.replace(".", "/");
            } else if (source.contains("\\")) {
                source = source.replaceAll("\\\\", "/");
            }
            if (!source.endsWith("/")) {
                source += "/";
            }
            return source;
        }

    }

    /**
     * 获取实体类
     *
     * @return 返回实体类
     */
    public Item getEntity() {
        return entity;
    }

    /**
     * 获取 DAO 接口
     *
     * @return 返回 DAO 接口
     */
    public Item getDao() {
        return dao;
    }

    /**
     * 获取 DAO 实现类
     *
     * @return 返回 DAO 实现类
     */
    public Item getDaoImpl() {
        return daoImpl;
    }

    /**
     * 获取实体类对应的 Xml 文件
     *
     * @return 返回实体类对应的 Xml 文件
     */
    public Item getMapperXml() {
        return mapperXml;
    }

    /**
     * 获取 Service 接口
     *
     * @return 返回 Service 接口
     */
    public Item getService() {
        return service;
    }

    /**
     * 获取 Service 实现类
     *
     * @return 返回 Service 实现类
     */
    public Item getServiceImpl() {
        return serviceImpl;
    }

    /**
     * 获取强制重写输出的表
     *
     * @return 返回强制重写输出的表
     */
    public Set<String> getOverwrite() {
        return overwrite;
    }

    /**
     * 设置实体类
     *
     * @param entity 实体类
     */
    void setEntity(Item entity) {
        this.entity = entity;
    }

    /**
     * 设置 DAO 接口
     *
     * @param dao DAO 接口
     */
    void setDao(Item dao) {
        this.dao = dao;
    }

    /**
     * 设置 DAO 实现类
     *
     * @param daoImpl DAO 实现类
     */
    void setDaoImpl(Item daoImpl) {
        this.daoImpl = daoImpl;
    }

    /**
     * 设置实体类对应的 Xml 文件
     *
     * @param mapperXml 实体类对应的 Xml 文件
     */
    void setMapperXml(Item mapperXml) {
        this.mapperXml = mapperXml;
    }

    /**
     * 设置 Service 接口
     *
     * @param service Service 接口
     */
    void setService(Item service) {
        this.service = service;
    }

    /**
     * 设置 Service 实现类
     *
     * @param serviceImpl Service 实现类
     */
    void setServiceImpl(Item serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    /**
     * 设置强制重写输出的表
     *
     * @param overwrite 强制重写输出的表
     */
    void setOverwrite(Set<String> overwrite) {
        this.overwrite = overwrite;
    }

}