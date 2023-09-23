package com.meti;

public record ImportRenderer(ImportNode importNode) implements Renderer {
    @Override
    public String render() {
        return "import { " + importNode().child() + " } from " + importNode().parent() + ";\n";
    }
}