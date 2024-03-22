package com.meti.compile;

import com.meti.collect.option.Option;
import com.meti.collect.result.ExceptionalStream;
import com.meti.collect.result.Result;
import com.meti.collect.result.ThrowableOption;
import com.meti.collect.stream.Collectors;
import com.meti.compile.node.*;
import com.meti.java.JavaString;

import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public class RenderingStage {
    static Result<JavaString, CompileException> renderExpression(Node node) {
        return $Result(() -> {
            var root = renderValue(node).$();
            var withChildren = renderChildren(root).$();
            return withChildren.render()
                    .map(JavaString::from)
                    .into(ThrowableOption::new)
                    .orElseThrow(() -> new CompileException("Failed to render: '%s'".formatted(node)))
                    .$();
        });
    }

    private static Result<Node, CompileException> renderValue(Node node) {
        return node.apply("value")
                .flatMap(Attribute::asNode)
                .map(RenderingStage::renderExpression)
                .map(result -> result.mapValue(value -> node.with("value", new NodeAttribute(new Content(value, 0))).orElse(node)))
                .orElse(Ok(node));
    }

    private static Result<Node, CompileException> renderChildren(Node root) {
        return root.apply("children")
                .flatMap(Attribute::asListOfNodes)
                .map(children -> children.stream()
                        .map(RenderingStage::renderExpression)
                        .into(ExceptionalStream::new)
                        .mapInner(JavaString::inner)
                        .mapInner(inner -> new Content(inner, 0))
                        .collect(Collectors.exceptionally(Collectors.toList())))
                .map(result -> result.mapValue(value -> root.with("children", new NodeListAttribute(value)).orElse(root)))
                .orElse(Ok(root));
    }
}
