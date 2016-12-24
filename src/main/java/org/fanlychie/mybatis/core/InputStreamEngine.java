package org.fanlychie.mybatis.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

/**
 * 输入流引擎
 * 
 * @author fanlychie
 */
public class InputStreamEngine {

	private static VelocityEngine engine = new VelocityEngine();

	public static void evaluate(Context context, Writer writer, String pathname) throws IOException {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(pathname)))) {
			String read;
			StringBuilder builder = new StringBuilder();
			while ((read = reader.readLine()) != null) {
				builder.append(read).append("\n");
			}
			String template = builder.substring(0, builder.length() - 1);
			engine.evaluate(context, writer, "", template);
		}
	}

}