package com.meti.app.compile.common.string;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record StringLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        if (text.startsWithChar('\"') && text.endsWithChar('\"')) {
            return new Some<>(new String(text));
        } else {
            return new None<>();
        }
    }
}
