package org.fanlychie.mybatis;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.fanlychie.mybatis.core.TemplateConsumer;
import org.fanlychie.mybatis.core.TemplateProducer;
import org.fanlychie.mybatis.schema.DBConnection;
import org.fanlychie.mybatis.schema.Scanner;
import org.fanlychie.mybatis.schema.SchemaMetaData;
import org.fanlychie.mybatis.schema.Table;

/**
 * 模板文件生成器
 * 
 * @author fanlychie
 */
public class TemplateGenerator {
	
	/**
	 * 生成模板文件
	 */
	public static void generate() throws Throwable {
		Connection conn = DBConnection.getConnection(ConfigXmlParser.parseDataSource());
		SchemaMetaData schemaMetaData = new SchemaMetaData(ConfigXmlParser.parseSchema(), conn);
		List<Table> tables = schemaMetaData.getTables();
		Scanner scanner = ConfigXmlParser.parseScanner();
		Map<String, Object> props = ConfigXmlParser.getProps();
		TemplateProducer producer = new TemplateProducer(scanner.getTemplateVmsPath());
		for (String templateClass : scanner.getTemplateClasses()) {
			producer.produce(tables, (Template) Class.forName(templateClass).newInstance(), props);
		}
		new TemplateConsumer().consume();
	}
	
}