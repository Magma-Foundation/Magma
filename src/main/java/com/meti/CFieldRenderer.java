package com.meti;

public class CFieldRenderer extends CompoundRenderer {
    private final Node field;

    public CFieldRenderer(Node field) {
        this.field = field;
    }

    @Override
    protected Stream<Renderer> stream() {
        return new ArrayStream<>(new IntegerTypeRenderer(field));
    }
}
