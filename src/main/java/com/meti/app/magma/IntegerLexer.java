package com.meti.app.magma;

import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.clang.Processor;
import com.meti.app.compile.feature.IntegerNode;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

public record IntegerLexer(Input root) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return new Some<>(new IntegerNode(Integer.parseInt(root.compute())));
    }
}
