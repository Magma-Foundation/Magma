package com.meti;

import java.util.List;

public record Function(String name,
                       List<String> parameters,
                       String returnType,
                       String body) implements Node {

    @Override
    public String render() {
        var renderedParameters = renderParameters();
        return returnType + " " + name + "(" + renderedParameters + ")" + body;
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
