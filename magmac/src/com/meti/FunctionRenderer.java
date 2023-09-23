package com.meti;

public record FunctionRenderer(MethodNode node) implements Renderer {
    @Override
    public String render() {
        return "export class def " + node().name() + node().parameters() + " => " + node().body();
    }
}