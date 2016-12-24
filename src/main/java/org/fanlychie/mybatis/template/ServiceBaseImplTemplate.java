package org.fanlychie.mybatis.template;

import java.util.Map;

import org.fanlychie.mybatis.ConfigXmlParser;
import org.fanlychie.mybatis.Template;

/**
 * 实体类业务层基类模板
 * 
 * @author fanlychie
 */
public class ServiceBaseImplTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", ConfigXmlParser.getOutput().getServiceImpl().getPackname());
		params.put("epackage", ConfigXmlParser.getOutput().getEntity().getPackname());
		params.put("dpackage", ConfigXmlParser.getOutput().getDao().getPackname());
		params.put("spackage", ConfigXmlParser.getOutput().getService().getPackname());
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return ConfigXmlParser.getOutput().getServiceImpl() + "BaseServiceImpl.java";
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