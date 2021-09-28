package com.meti;

import java.util.List;
import java.util.stream.Stream;

public record Function(String name,
                       List<String> parameters,
                       String returnType,
                       Node body) implements Node {

    @Override
    public Group group() {
        return Group.Function;
    }

    @Override
    public String render() {
        var renderedParameters = renderParameters();
        return returnType + " " + name + "(" + renderedParameters + ")" + body.render();
    }

    @Override
    public Stream<Type> streamNodes() {
        return Stream.of(Type.Body);
    }

    @Override
    public Node apply(Type type) {
        return body;
    }

    @Override
    public Node withNode(Node node) {
        return new Function(name, parameters, returnType, node);
    }

    private StringBuilder renderParameters() {
        var builder = new StringBuilder();
        if (!parameters.isEmpty()) {
            var first = parameters.get(0);
            if (!first.isBlank()) {
                builder.append(first);
            }

            for (int i = 1; i < parameters.size(); i++) {
                builder.append(",").append(parameters.get(i));
            }
        }
        return builder;
    }
}
