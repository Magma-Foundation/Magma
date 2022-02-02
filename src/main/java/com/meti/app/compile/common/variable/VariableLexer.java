package com.meti.app.compile.common.variable;

import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.feature.scope.Variable;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record VariableLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        return new Some<>(new Variable(text));
    }
}
