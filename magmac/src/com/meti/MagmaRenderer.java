package com.meti;

public record MagmaRenderer(Node node) implements Renderer {
    static Iterator<Renderer> enumerateRenderers(Node node) {
        return Iterators.from(
                new ImportRenderer(node),
                new BlockRenderer(node),
                new FunctionRenderer(node)
        );
    }

    @Override
    public Option<String> render() {
        return enumerateRenderers(node())
                .map(Renderer::render)
                .head()
                .flatMap(value -> value);
    }
}