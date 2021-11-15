package com.meti.app.process.clang;

import com.meti.api.stream.ArrayStream;
import com.meti.api.stream.Stream;
import com.meti.app.node.Node;
import com.meti.app.process.CompoundProcessor;
import com.meti.app.process.Processor;

public class CNodeRenderer extends CompoundProcessor<String> {
    private final Node value;

    public CNodeRenderer(Node value) {
        this.value = value;
    }

    @Override
    protected Stream<Processor<String>> stream() {
        return new ArrayStream<>(
                new BlockRenderer(value),
                new CImportRenderer(value),
                new DeclarationRenderer(value),
                new FunctionRenderer(value),
                new IntegerRenderer(value),
                new ReturnRenderer(value),
                new SequenceRenderer(value));
    }
}
