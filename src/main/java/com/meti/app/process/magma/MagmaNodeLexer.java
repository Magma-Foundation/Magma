package com.meti.app.process.magma;

import com.meti.api.stream.ArrayStream;
import com.meti.api.stream.Stream;
import com.meti.app.Input;
import com.meti.app.node.Node;
import com.meti.app.process.CompoundProcessor;
import com.meti.app.process.Processor;
import com.meti.app.process.clang.BlockLexer;
import com.meti.app.process.clang.IntLexer;
import com.meti.app.process.clang.ReturnLexer;

public class MagmaNodeLexer extends CompoundProcessor<Node> {
    private final Input root;

    public MagmaNodeLexer(Input root) {
        this.root = root;
    }

    @Override
    protected Stream<Processor<Node>> stream() {
        return new ArrayStream<>(
                new BlockLexer(root),
                new IntLexer(root),
                new ReturnLexer(root),
                new MagmaFunctionLexer(root));
    }
}
