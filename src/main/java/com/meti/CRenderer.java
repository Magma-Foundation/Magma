package com.meti;

import com.meti.clang.CImportRenderer;
import com.meti.clang.CReturnRenderer;
import com.meti.clang.Renderer;
import com.meti.feature.Node;

import java.util.stream.Stream;

public record CRenderer(Node node) {
    public Output render() {
        return Stream.of(
                new CImportRenderer(node),
                new CReturnRenderer(node),
                new IntegerRenderer(node))
                .map(Renderer::render)
                .map(option -> option.map(Stream::of))
                .flatMap(option -> option.orElse(Stream.empty()))
                .findFirst()
                .orElseThrow();
    }
}
