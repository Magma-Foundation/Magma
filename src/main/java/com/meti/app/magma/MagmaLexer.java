package com.meti.app.magma;

import com.meti.api.ArrayStream;
import com.meti.api.Stream;
import com.meti.app.clang.Processor;
import com.meti.app.compile.AbstractStage;
import com.meti.app.compile.feature.BlockLexer;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

public final class MagmaLexer extends AbstractStage<Node, Processor<Node>> {
    private final Input input;

    public MagmaLexer(Input input) {
        this.input = input;
    }

    @Override
    protected Stream<Processor<Node>> createProcessors() {
        return new ArrayStream<>(
                new FunctionLexer(input),
                new BlockLexer(input),
                new ImportLexer(input),
                new ReturnLexer(input),
                new IntegerLexer(input));
    }
}
