package com.meti.compile.block;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.compile.Node;
import com.meti.compile.Renderer;

import java.util.stream.Collectors;

public record BlockRenderer(Node node) implements Renderer {

    @Override
    public Option<JavaString> render() {
        return this.node()
                .getLines()
                .map(lines -> lines.stream().collect(Collectors.joining("\t", "{\n", "\n}")));
    }
}