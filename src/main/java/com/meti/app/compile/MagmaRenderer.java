package com.meti.app.compile;

import com.meti.app.compile.block.BlockRenderer;
import com.meti.app.compile.clazz.ObjectRenderer;
import com.meti.app.compile.declare.DeclarationRenderer;
import com.meti.app.compile.function.FunctionRenderer;
import com.meti.app.compile.imports.ImportRenderer;
import com.meti.core.Option;
import com.meti.iterate.Iterators;
import com.meti.java.Set;
import com.meti.java.String_;

import static com.meti.java.JavaSet.of;

public record MagmaRenderer(Node node) implements Renderer {
    @Override
    public Option<String_> render() {
        Set<? extends Renderer> renderers = of(
                new ObjectRenderer(node()),
                new BlockRenderer(node()),
                new DeclarationRenderer(node()),
                new FunctionRenderer(node()),
                new ImportRenderer(node()),
                new ContentRenderer(node()));

        return renderers.iter()
                .map(Renderer::render)
                .flatMap(Iterators::fromOption)
                .head();
    }
}