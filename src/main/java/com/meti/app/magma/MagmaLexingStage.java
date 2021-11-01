package com.meti.app.magma;

import com.meti.api.ArrayStream;
import com.meti.api.Stream;
import com.meti.app.clang.Processor;
import com.meti.app.compile.AbstractStage;
import com.meti.app.compile.feature.BlockLexer;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

public final class MagmaLexingStage extends AbstractStage<Node, Processor<Node>> {
    private final Input root;

    public MagmaLexingStage(Input root) {
        this.root = root;
    }

    @Override
    protected Stream<Processor<Node>> createProcessors() {
        return new ArrayStream<>(
                new BlockLexer(root),
                new ImportLexer(root),
                new ReturnLexer(root),
                new IntegerLexer(root));
    }
}
