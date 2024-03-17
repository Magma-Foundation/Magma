package com.meti.compile.magma;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public record ObjectRenderer(Node classNode) implements com.meti.compile.node.Renderer {
    @Override
    public Option<String> render() {
        return $Option(() -> {
            var flagsString = classNode()
                    .findFlags().$()
                    .stream()
                    .collect(Collectors.joining(new JavaString(" ")))
                    .map(value -> value.concatSlice(" "))
                    .orElse(JavaString.Empty);

            var name = classNode().findName().$();
            var renderedContent = classNode().findValueAsNode().$().render().$();

            return "\n" + flagsString + "object " + name + " = " + renderedContent;
        });
    }
}