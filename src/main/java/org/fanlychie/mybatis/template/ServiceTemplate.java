package org.fanlychie.mybatis.template;

import java.util.Map;

import org.fanlychie.mybatis.ConfigXmlParser;
import org.fanlychie.mybatis.Template;

/**
 * 实体类业务层接口模板
 * 
 * @author fanlychie
 */
public class ServiceTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", ConfigXmlParser.getOutput().getService().getPackname());
		params.put("epackage", ConfigXmlParser.getOutput().getEntity().getPackname());
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return ConfigXmlParser.getOutput().getService() + tableName + "Service.java";
	}

	@Override
	public boolean isMultiFileMode() {
		return true;
	}

	@Override
	public boolean isForceOverride() {
		return false;
	}

}