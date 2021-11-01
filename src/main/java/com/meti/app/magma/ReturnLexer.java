package com.meti.app.magma;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.clang.Processor;
import com.meti.app.compile.feature.Return;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

public record ReturnLexer(Input root) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        if (root.startsWithString("return ")) {
            var value = root.truncate("return ");
            return new Some<>(new Return(new Content(new Input(value))));
        }
        return new None<>();
    }
}
