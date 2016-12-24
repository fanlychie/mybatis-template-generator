package org.fanlychie.mybatis.directive;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.fanlychie.mybatis.core.InputStreamEngine;

/**
 * 生成表的字段的 getters 和 setters 方法的指令。
 * 
 * 指令使用：#getset
 * 
 * @author fanlychie
 */
public class GenerateGetSetDirective extends Directive {

	@Override
	public String getName() {
		return "getset";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		InputStreamEngine.evaluate(context, writer, "org/fanlychie/mybatis/directive/vm/getset.vm");
		return false;
	}

}