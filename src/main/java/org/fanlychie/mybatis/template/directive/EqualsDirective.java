package org.fanlychie.mybatis.template.directive;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.fanlychie.mybatis.template.engine.InputStreamEngine;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

/**
 * 生成 equals 方法的指令, 用法:
 * #equals
 * #equals(字符串参数)
 * #equals([字符串参数1, 字符串参数2...])
 * Created by fanlychie on 2017/2/1.
 */
public class EqualsDirective extends Directive {

    @Override
    public String getName() {
        return "equals";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
            throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object arg = null;
        try {
            arg = node.jjtGetChild(0).value(context);
            // #equals 没有参数时此处会抛 NullPointerException, 忽略该异常
        } catch (NullPointerException e) {}
        // 参数不为 null 时, 根据传入的参数生成; 否则根据所有的列字段生成
        if (arg != null) {
            List param = null;
            if (arg instanceof String) {
                param = Arrays.asList(arg);
            } else if (arg instanceof List) {
                param = (List) arg;
            }
            context.put("_params", param);
        }
        InputStreamEngine.evaluate(context, writer, "org/fanlychie/mybatis/template/directive/vm/equals.vm");
        return false;
    }

}