package org.fanlychie.mybatis.template.generator;

import java.util.Map;
import org.fanlychie.jdbc.template.Template;

/**
 * 实体类标记接口模板
 * 
 * @author fanlychie
 */
public class EntityBaseTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return "src/main/java/org/fanlychie/entity/Entity.java";
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