package com.meti.process;

import com.meti.Input;
import com.meti.Processor;
import com.meti.node.Node;
import com.meti.stream.ArrayStream;
import com.meti.stream.Stream;

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
