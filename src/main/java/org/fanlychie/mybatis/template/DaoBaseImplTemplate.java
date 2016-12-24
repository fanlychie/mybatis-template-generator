package org.fanlychie.mybatis.template;

import java.util.Map;

import org.fanlychie.mybatis.ConfigXmlParser;
import org.fanlychie.mybatis.Template;

/**
 * 实体类数据访问层实现类基类模板
 * 
 * @author fanlychie
 */
public class DaoBaseImplTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", ConfigXmlParser.getOutput().getDaoImpl().getPackname());
		params.put("dpackage", ConfigXmlParser.getOutput().getDao().getPackname());
		params.put("epackage", ConfigXmlParser.getOutput().getEntity().getPackname());
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return ConfigXmlParser.getOutput().getDaoImpl() + "BaseDaoImpl.java";
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