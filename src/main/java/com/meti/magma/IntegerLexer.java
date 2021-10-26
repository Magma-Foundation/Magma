package com.meti.magma;

import com.meti.Input;
import com.meti.clang.Processor;
import com.meti.feature.IntegerNode;
import com.meti.feature.Node;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerLexer(Input root) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return new Some<>(new IntegerNode(Integer.parseInt(root.compute())));
    }
}
