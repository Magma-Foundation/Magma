package com.meti.process;

import com.meti.Processor;
import com.meti.node.Node;
import com.meti.stream.ArrayStream;
import com.meti.stream.Stream;

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
