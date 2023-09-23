package com.meti;

import java.util.stream.Collectors;

public record BlockRenderer(BlockNode blockNode) implements Renderer {
    private String render1() {
        return blockNode().lines().stream().collect(Collectors.joining("\t", "{\n", "\n}"));
    }

    @Override
    public Option<String> render() {
        return Some.apply(render1());
    }
}