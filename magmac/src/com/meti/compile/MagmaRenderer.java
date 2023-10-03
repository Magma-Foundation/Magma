package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.api.result.Result;
import com.meti.compile.block.BlockRenderer;
import com.meti.compile.function.FunctionRenderer;
import com.meti.compile.imports.ImportRenderer;
import com.meti.compile.node.Node;
import com.meti.compile.struct.StructRenderer;

public record MagmaRenderer(Node node) implements Renderer {
    static Iterator<Renderer> enumerateRenderers(Node node) {
        return Iterators.from(
                new ImportRenderer(node),
                new BlockRenderer(node),
                new FunctionRenderer(node),
                new StructRenderer(node)
        );
    }

    @Override
    public Option<Result<JavaString, CompileException>> render() {
        return enumerateRenderers(node())
                .map(Renderer::render)
                .flatMap(Iterators::fromOption)
                .head();
    }
}