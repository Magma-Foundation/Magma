package com.meti;

import java.util.stream.Collectors;

public record BlockRenderer(BlockNode blockNode) implements Renderer {
    @Override
    public String render() {
        return blockNode().lines().stream().collect(Collectors.joining("\t", "{\n", "\n}"));
    }
}