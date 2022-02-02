package com.meti.app.compile.parse;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.magma.FunctionType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;

public final class MagmaParser extends AbstractParser {
    public MagmaParser(List<? extends Node> input) {
        super(input);
    }

    @Override
    protected State createInitialState() {
        return new State().mapScope(scope -> {
            // TODO: Do generic type here later
            var type = new FunctionType(Primitive.Void);
            return scope.define(new EmptyField("=", type));
        });
    }

    @Override
    protected Stream<Parser> streamParsers(State state) {
        return Streams.apply(
                new BlockParser(state),
                new ImplementationParser(state),
                new VariableParser(state)
        );
    }
}
