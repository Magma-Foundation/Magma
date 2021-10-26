package com.meti.magma;

import com.meti.AbstractStage;
import com.meti.Input;
import com.meti.clang.Processor;
import com.meti.feature.Node;

import java.util.stream.Stream;

public final class MagmaLexingStage extends AbstractStage<Node, Processor<Node>> {
    private final Input root;

    public MagmaLexingStage(Input root) {
        this.root = root;
    }

    @Override
    protected Stream<Processor<Node>> createProcessors() {
        return Stream.of(new ImportLexer(root),
                new ReturnLexer(root),
                new IntegerLexer(root));
    }
}
