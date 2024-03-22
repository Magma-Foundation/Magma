package com.meti.compile.magma;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public record ObjectRenderer(Node classNode) implements com.meti.compile.node.Renderer {
    @Override
    public Option<String> render() {
        return $Option(() -> {
            var flagsString = this.classNode().apply("flags").flatMap(value1 -> value1.asListOfStrings()).$()
                    .stream()
                    .collect(Collectors.joining(JavaString.from(" ")))
                    .map(value -> value.concatSlice(" "))
                    .orElse(JavaString.Empty);

            var name = this.classNode().apply("name").flatMap(Attribute::asString).$();
            var renderedContent = this.classNode().apply("body").flatMap(Attribute::asNode).$().render().$();

            return "\n" + flagsString + "object " + name + " = " + renderedContent;
        });
    }
}