package com.meti.compile.magma;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Renderer;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public class StatementRenderer implements Renderer {
    private final Node node;

    public StatementRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Option<String> render() {
        return $Option(() -> {
            var value = node.apply("value").$()
                    .asNode().$()
                    .apply("value").$()
                    .asString().$();

            var indent = node.apply("indent").$()
                    .asInteger().$();

            String suffix;
            if(node.is("terminating-statement")) {
                suffix = ";";
            } else {
                suffix = "";
            }

            return "\t".repeat(indent) + value + suffix;
        });
    }
}
