package com.meti;

import java.util.stream.Collectors;

public record BlockRenderer(Node node) implements Renderer {

    @Override
    public Option<String> render() {
        return this.node()
                .getLines()
                .map(lines -> lines.stream().collect(Collectors.joining("\t", "{\n", "\n}")));
    }
}