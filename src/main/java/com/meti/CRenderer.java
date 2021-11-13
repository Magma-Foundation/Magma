package com.meti;

public record CRenderer(Node value) implements Renderer {
    @Override
    public Option<String> render() throws CompileException {
        try {
            return new ArrayStream<>(
                    new BlockRenderer(value),
                    new IntegerRenderer(value),
                    new ReturnRenderer(value))
                    .map(AbstractRenderer::render)
                    .flatMap(OptionStream::new)
                    .first();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
