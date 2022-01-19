package com.meti.app.compile.magma;

import com.meti.api.option.Option;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record UnaryLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return text.firstIndexOfChar(' ').map(separator -> {
            var callerString = text.slice(0, separator);
            var caller = new InputNode(callerString);

            var calleeString = text.slice(separator + 1);
            var callee = new InputNode(calleeString);

            return new UnaryOperation(caller, callee);
        });
    }
}
