package com.meti;

public final class CRenderer {
    private final String name;
    private final String type;

    public CRenderer(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String render() {
        return renderFunctionHeader(type) + "{return 0;}";
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
}
