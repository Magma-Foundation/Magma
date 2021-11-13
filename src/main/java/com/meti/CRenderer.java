package com.meti;

import java.util.stream.Collectors;

public final class CRenderer {
    private final String name;
    private final String type;
    private final int value;

    public CRenderer(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String render() throws CompileException {
        return renderFunctionHeader(type) + renderBlock();
    }

    private String renderFunctionHeader(String input) {
        String typeString;
        if (input.equals("I16")) {
            typeString = "int";
        } else {
            typeString = "unsigned int";
        }
        return typeString + " " + name + "()";
    }

    private String renderBlock() throws CompileException {
        return "{" + renderReturn(new Return(new IntegerNode(value))) + "}";
    }

    private String renderReturn(Node value) throws CompileException {
        var types = value.stream(Attribute.Group.Node).collect(Collectors.toList());
        var current = value;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNode();
            var childOutput = renderNode(child);
            current = current.with(new Content(childOutput));
        }

        return new ReturnRenderer(current)
                .render()
                .orElse("");
    }

    private String renderNode(Node value) throws CompileException {
        return new IntegerRenderer(value)
                .render()
                .orElseThrow(() -> new CompileException("Cannot render: " + value));
    }

}
