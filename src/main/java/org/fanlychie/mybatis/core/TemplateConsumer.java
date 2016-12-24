package org.fanlychie.mybatis.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;

import org.fanlychie.mybatis.TemplateFileQueue;
import org.fanlychie.mybatis.TemplateFileQueue.Entry;

/**
 * 模板文件消费者
 * 
 * @author fanlychie
 */
public class TemplateConsumer {

	/**
	 * 消费队列
	 */
	public void consume() throws Throwable {
		while (TemplateFileQueue.hasNext()) {
			Entry entry = TemplateFileQueue.pop();
			printMessage(entry);
			write(entry.getContent(), entry.getFile());
		}
		System.out.println("[C] ------------------------------------------------------------------------\n");
		System.out.println("[C] BUILD SUCCESS\n");
		System.out.println("[C] ------------------------------------------------------------------------\n");
	}

	/**
	 * 写出内容到文件
	 * 
	 * @param text
	 *            文件内容
	 * @param file
	 *            文件对象
	 */
	private void write(String text, File file) throws Throwable {
		try (BufferedReader reader = new BufferedReader(new StringReader(text));
				BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			int read;
			char[] buffer = new char[1024 * 1024 / 2];
			while ((read = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, read);
			}
		}
	}

	/**
	 * 打印消息
	 */
	private void printMessage(Entry entry) {
		String pathname = entry.getFile().getAbsolutePath();
		pathname = pathname.replace("\\", "/");
		while (pathname.indexOf("/..") != -1) {
			int index = pathname.indexOf("../");
			String lpath = pathname.substring(0, index - 1);
			String rpath = pathname.substring(index + 2);
			pathname = lpath.substring(0, lpath.lastIndexOf("/")) + rpath;
		}
		pathname = pathname.replace("/.", "");
		String info = String.format("%s %s %s", "[C]", pathname, "\n");
		System.out.println(info);
	}

}