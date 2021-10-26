package com.meti;

import com.meti.clang.AbstractRenderer;
import com.meti.clang.Renderer;
import com.meti.feature.Node;
import com.meti.output.Output;

import java.util.stream.Stream;

public abstract class AbstractRenderingStage {
    protected final Node node;

    public AbstractRenderingStage(Node node) {
        this.node = node;
    }

    public Output render() {
        return createRenderers()
                .map(Renderer::render)
                .map(option -> option.map(Stream::of))
                .flatMap(option -> option.orElse(Stream.empty()))
                .findFirst()
                .orElseThrow();
    }

    protected abstract Stream<AbstractRenderer> createRenderers();
}
