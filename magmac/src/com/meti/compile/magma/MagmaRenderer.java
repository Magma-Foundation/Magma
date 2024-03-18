package com.meti.compile.magma;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Streams;
import com.meti.compile.node.Node;
import com.meti.compile.node.Renderer;

public record MagmaRenderer(Node node) implements Renderer {
    @Override
    public Option<String> render() {
        return Streams.from(new ObjectRenderer(node),
                        new ImplementsRenderer(node))
                .map(Renderer::render)
                .flatMap(Streams::fromOption)
                .next();
    }
}