package com.meti.compile.imports;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.compile.Attribute;
import com.meti.compile.Node;
import com.meti.compile.Renderer;

import static com.meti.api.option.Options.$Option;

public record ImportRenderer(Node importNode) implements Renderer {

    @Override
    public Option<JavaString> render() {
        return $Option(() -> {
            var child = this.importNode().apply(JavaString.apply("child"))
                    .flatMap(Attribute::asNode)
                    .flatMap(node -> node.apply(JavaString.apply("value")))
                    .flatMap(Attribute::asString).$().value();
            var parent = this.importNode().apply(JavaString.apply("parent"))
                    .flatMap(Attribute::asNode)
                    .flatMap(node -> node.apply(JavaString.apply("value")))
                    .flatMap(Attribute::asString).$().value();
            return new JavaString("import { " + child + " } from " + parent + ";\n");
        });
    }
}