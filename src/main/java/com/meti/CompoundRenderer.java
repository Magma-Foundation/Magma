package com.meti;

public abstract class CompoundRenderer implements Renderer {
    @Override
    public Option<String> render() throws CompileException {
        try {
            return stream()
                    .map(Renderer::render)
                    .flatMap(OptionStream::new)
                    .first();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Stream<Renderer> stream();
}
