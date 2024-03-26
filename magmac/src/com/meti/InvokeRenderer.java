package com.meti;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class InvokeRenderer implements Renderer {
    private final Node node;

    public InvokeRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (node.is("invoke")) {
            var callerOptional = node.apply("caller")
                    .flatMap(Attribute::asNode)
                    .flatMap(node -> node.apply(Content.VALUE))
                    .flatMap(Attribute::asString);
            if (callerOptional.isEmpty()) return Optional.empty();
            var caller = callerOptional.get();

            var arguments = node.apply("arguments")
                    .flatMap(Attribute::asListOfNodes)
                    .orElse(Collections.emptyList())
                    .stream()
                                .map(element -> element.apply(Content.VALUE))
                                .flatMap(Optional::stream)
                                .map(Attribute::asString)
                                .flatMap(Optional::stream)
                                .collect(Collectors.toList());

            return Optional.of(caller + "(" + String.join(", ", arguments) + ")");
        } else {
            return Optional.empty();
        }
    }
}
