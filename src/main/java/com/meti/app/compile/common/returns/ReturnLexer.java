package com.meti.app.compile.common.returns;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.feature.function.Return;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record ReturnLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        if (text.startsWithSlice("return ")) {
            var valueString = text.slice("return ".length(), text.size());
            var value = new InputNode(valueString);
            return new Some<>(new Return(value));
        }
        return new None<>();
    }
}
