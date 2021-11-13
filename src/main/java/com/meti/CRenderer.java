package com.meti;

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
        return "{" + renderReturn(new Return(new IntegerNode(value)).apply().asNode()) + "}";
    }

    private String renderReturn(Node value) throws CompileException {
        return "return " + new IntegerRenderer(value).render() + ";";
    }
}
