package com.meti;

public class MagmaNodeLexer extends CompoundProcessor<Node> {
    private final String value;

    public MagmaNodeLexer(String value) {
        this.value = value;
    }

    @Override
    protected Stream<Processor<Node>> stream() {
        return new ArrayStream<>(new FunctionLexer(value));
    }
}
