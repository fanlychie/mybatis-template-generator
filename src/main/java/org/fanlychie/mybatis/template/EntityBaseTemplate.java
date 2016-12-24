package org.fanlychie.mybatis.template;

import java.util.Map;

import org.fanlychie.mybatis.ConfigXmlParser;
import org.fanlychie.mybatis.Template;

/**
 * 实体类标记接口模板
 * 
 * @author fanlychie
 */
public class EntityBaseTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", ConfigXmlParser.getOutput().getEntity().getPackname());
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return ConfigXmlParser.getOutput().getEntity() + "Entity.java";
	}

	@Override
	public boolean isMultiFileMode() {
		return false;
	}

	@Override
	public boolean isForceOverride() {
		return false;
	}

}