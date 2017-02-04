package org.fanlychie.mybatis.template.exception;

/**
 * 解析 XML 配置文件异常
 * Created by fanlychie on 2017/2/2.
 */
public class ParseXmlException extends RuntimeException {

    public ParseXmlException(String message) {
        super(message);
    }

}