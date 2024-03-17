package com.meti.compile.magma;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;
import com.meti.compile.node.Renderer;

public record MagmaRenderer(Node node) implements Renderer {
    @Override
    public Option<String> render() {
        return new ObjectRenderer(node()).render();
    }
}