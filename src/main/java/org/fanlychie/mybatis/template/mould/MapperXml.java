package org.fanlychie.mybatis.template.mould;

import org.fanlychie.mybatis.template.Template;
import org.fanlychie.mybatis.template.config.Configuration;

import java.util.Map;

/**
 * 实体类数据访问层XML模板
 * Created by fanlychie on 2017/2/2.
 */
public class MapperXml implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", Configuration.getOutput().getDao().getPackname());
		params.put("epackage", Configuration.getOutput().getEntity().getPackname());
	}

	@Override
	public String getFileOutputPath(String tableName) {
		return Configuration.getOutput().getMapperXml() + tableName + ".xml";
	}

	@Override
	public boolean isMultiFileMode() {
		return true;
	}

	@Override
	public boolean isOverride() {
		return false;
	}

}