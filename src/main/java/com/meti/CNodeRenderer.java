package com.meti;

public class CNodeRenderer extends CompoundRenderer {
    private final Node value;

    public CNodeRenderer(Node value) {
        this.value = value;
    }

    @Override
    protected Stream<Renderer> stream() {
        return new ArrayStream<>(
                new BlockRenderer(value),
                new IntegerRenderer(value),
                new ReturnRenderer(value),
                new SequenceRenderer(value));
    }
}
