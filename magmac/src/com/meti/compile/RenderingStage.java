package com.meti.compile;

import com.meti.collect.result.Result;
import com.meti.collect.result.ThrowableOption;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.NodeAttribute;
import com.meti.java.JavaString;

import static com.meti.collect.result.Ok.Ok;
import static com.meti.collect.result.Results.$Result;

public class RenderingStage {
    static Result<JavaString, CompileException> renderExpression(Node node) {
        return $Result(() -> {
            var root = node.apply("value")
                    .flatMap(Attribute::asNode)
                    .map(RenderingStage::renderExpression)
                    .map(result -> result.mapValue(value -> node.with("value", new NodeAttribute(new Content(value, 0))).orElse(node)))
                    .orElse(Ok(node))
                    .$();

            return root.render()
                    .map(JavaString::from)
                    .into(ThrowableOption::new)
                    .orElseThrow(() -> new CompileException("Failed to render: '%s'".formatted(node)))
                    .$();
        });
    }
}
