package com.meti;

public class MagmaNodeLexer extends CompoundProcessor<Node> {
    private final Input root;

    public MagmaNodeLexer(Input root) {
        this.root = root;
    }

    @Override
    protected Stream<Processor<Node>> stream() {
        return new ArrayStream<>(
                new BlockLexer(root),
                new FunctionLexer(root),
                new IntLexer(root),
                new ReturnLexer(root));
    }
}
