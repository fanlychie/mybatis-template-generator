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

/**
 * 生成类的属性指令, 用法:
 * #fields
 * Created by fanlychie on 2017/2/1.
 */
public class FieldsDirective extends Directive {

    @Override
    public String getName() {
        return "fields";
    }

    @Override
    public int getType() {
        return LINE;
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node)
            throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        InputStreamEngine.evaluate(context, writer, "org/fanlychie/mybatis/template/directive/vm/fields.vm");
        return false;
    }

}