package demo.test;

import org.fanlychie.mybatis.TemplateGenerator;

import java.io.File;

public class Test {

	public static void main(String[] args) throws Throwable {
		delete(new File("C:/project/src/main/java/"));
		delete(new File("C:/project/src/main/java/resources"));
		TemplateGenerator.generate();
	}
	
	public static void delete(File src) {
		if (!src.exists()) {
			return ;
		}
		if (src.isFile()) {
			src.delete();
		} else if (src.isDirectory()) {
			File[] files = src.listFiles();
			for (File item : files) {
				delete(item);
			}
		}
		src.delete();
	}

}