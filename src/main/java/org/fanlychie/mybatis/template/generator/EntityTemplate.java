package org.fanlychie.mybatis.template.generator;

import java.util.Map;
import org.fanlychie.jdbc.template.Template;

/**
 * 实体类模板
 * 
 * @author fanlychie
 */
public class EntityTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return "src/main/java/org/fanlychie/entity/" + tableName + ".java";
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