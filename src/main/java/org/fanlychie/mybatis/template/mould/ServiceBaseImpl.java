package org.fanlychie.mybatis.template.mould;

import org.fanlychie.mybatis.template.Template;
import org.fanlychie.mybatis.template.config.Configuration;

import java.util.Map;

/**
 * 实体类业务层基类模板
 * Created by fanlychie on 2017/2/2.
 */
public class ServiceBaseImpl implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		params.put("packname", Configuration.getOutput().getServiceImpl().getPackname());
		params.put("epackage", Configuration.getOutput().getEntity().getPackname());
		params.put("dpackage", Configuration.getOutput().getDao().getPackname());
		params.put("spackage", Configuration.getOutput().getService().getPackname());
	}

	@Override
	public String getFileOutputPath(String tableName) {
		return Configuration.getOutput().getServiceImpl() + "BaseServiceImpl.java";
	}

	@Override
	public boolean isMultiFileMode() {
		return false;
	}

	@Override
	public boolean isOverride() {
		return false;
	}

}