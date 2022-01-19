package com.meti.app.compile.common.integer;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record IntegerLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return text.map(this::toInteger).map(IntegerNode::new);
    }

    private Option<Integer> toInteger(String s) {
        try {
            return new Some<>(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
