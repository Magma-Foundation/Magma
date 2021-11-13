package com.meti;

public abstract class AbstractRenderer extends AbstractProcessor<String> {
    protected final Node value;
    private final Node.Type type;

    public AbstractRenderer(Node value, Node.Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    protected boolean isValid() {
        return value.is(type);
    }
}
