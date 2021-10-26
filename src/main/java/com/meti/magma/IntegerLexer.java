package com.meti.magma;

import com.meti.clang.Processor;
import com.meti.compile.Input;
import com.meti.compile.Node;
import com.meti.compile.feature.IntegerNode;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerLexer(Input root) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return new Some<>(new IntegerNode(Integer.parseInt(root.compute())));
    }
}
