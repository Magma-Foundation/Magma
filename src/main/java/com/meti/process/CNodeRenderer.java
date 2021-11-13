package com.meti.process;

import com.meti.Processor;
import com.meti.node.Node;
import com.meti.stream.ArrayStream;
import com.meti.stream.Stream;

public class CNodeRenderer extends CompoundProcessor<String> {
    private final Node value;

    public CNodeRenderer(Node value) {
        this.value = value;
    }

    @Override
    protected Stream<Processor<String>> stream() {
        return new ArrayStream<>(
                new BlockRenderer(value),
                new FunctionRenderer(value),
                new IntegerRenderer(value),
                new ReturnRenderer(value),
                new SequenceRenderer(value));
    }
}
