package org.fanlychie.mybatis.template.generator;

import java.util.Map;
import org.fanlychie.jdbc.template.Template;

/**
 * 实体类数据访问层XML模板
 * 
 * @author fanlychie
 */
public class EntityMapperXmlTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return "src/main/resources/org/fanlychie/mapper/" + tableName + ".xml";
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