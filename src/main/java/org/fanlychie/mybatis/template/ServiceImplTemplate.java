package org.fanlychie.mybatis.template;

import java.util.Map;

import org.fanlychie.mybatis.ConfigXmlParser;
import org.fanlychie.mybatis.Template;

/**
 * 实体类业务层模板
 * 
 * @author fanlychie
 */
public class ServiceImplTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", ConfigXmlParser.getOutput().getServiceImpl().getPackname());
		params.put("epackage", ConfigXmlParser.getOutput().getEntity().getPackname());
		params.put("dpackage", ConfigXmlParser.getOutput().getDao().getPackname());
		params.put("spackage", ConfigXmlParser.getOutput().getService().getPackname());
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return ConfigXmlParser.getOutput().getServiceImpl() + tableName + "ServiceImpl.java";
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