package com.meti.magma;

import com.meti.clang.Processor;
import com.meti.compile.Input;
import com.meti.compile.Node;
import com.meti.compile.feature.IntegerNode;
import com.meti.compile.feature.Return;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ReturnLexer(Input root) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        if (root.startsWithString("return ")) {
            var value = root.sliceWithPrefix("return ");
            return new Some<>(new Return(new IntegerNode(Integer.parseInt(value))));
        }
        return new None<>();
    }
}
