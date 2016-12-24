package org.fanlychie.mybatis.template;

import java.util.Map;

import org.fanlychie.mybatis.ConfigXmlParser;
import org.fanlychie.mybatis.Template;

/**
 * 实体类数据访问层XML模板
 * 
 * @author fanlychie
 */
public class MapperXmlTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", ConfigXmlParser.getOutput().getDao().getPackname());
		params.put("epackage", ConfigXmlParser.getOutput().getEntity().getPackname());
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return ConfigXmlParser.getOutput().getMapperXml() + tableName + ".xml";
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