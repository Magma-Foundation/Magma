package com.meti;

public record FunctionRenderer(MethodNode node) implements Renderer {
    private String render1() {
        return "export class def " + node().name() + node().parameters() + " => " + node().body();
    }

    @Override
    public Option<String> render() {
        return Some.apply(render1());
    }
}