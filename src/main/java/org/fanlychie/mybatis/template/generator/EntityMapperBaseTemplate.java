package org.fanlychie.mybatis.template.generator;

import java.util.Map;
import org.fanlychie.jdbc.template.Template;

/**
 * 实体类数据访问层基类接口模板
 * 
 * @author fanlychie
 */
public class EntityMapperBaseTemplate implements Template {

	@Override
	public void setContextParams(Map<String, Object> params) {
		
	}

	@Override
	public String getOutputFilePath(String tableName) {
		return "src/main/java/org/fanlychie/mapper/BaseMapper.java";
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