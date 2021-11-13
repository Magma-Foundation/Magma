package com.meti;

public abstract class AbstractRenderer implements Renderer {
    protected final Node value;
    private final Node.Type type;

    public AbstractRenderer(Node value, Node.Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Option<String> render() throws CompileException {
        if (value.is(type)) {
            var valueAsString = renderValid();
            return new Some<>(valueAsString);
        } else {
            return new None<>();
        }
    }

    protected abstract String renderValid() throws CompileException;
}
