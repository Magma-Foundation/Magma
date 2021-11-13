package com.meti.app.process;

import com.meti.api.stream.ArrayStream;
import com.meti.api.stream.Stream;
import com.meti.app.node.Node;

public class CFieldRenderer extends CompoundProcessor<String> {
    private final Node field;

    public CFieldRenderer(Node field) {
        this.field = field;
    }

    @Override
    protected Stream<Processor<String>> stream() {
        return new ArrayStream<>(new IntegerTypeRenderer(field));
    }
}
