package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.compile.block.BlockRenderer;
import com.meti.compile.function.FunctionRenderer;
import com.meti.compile.imports.ImportRenderer;

public record MagmaRenderer(Node node) implements Renderer {
    static Iterator<Renderer> enumerateRenderers(Node node) {
        return Iterators.from(
                new ImportRenderer(node),
                new BlockRenderer(node),
                new FunctionRenderer(node)
        );
    }

    @Override
    public Option<JavaString> render() {
        return enumerateRenderers(node())
                .map(Renderer::render)
                .flatMap(Iterators::fromOption)
                .head();
    }
}