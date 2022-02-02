package com.meti.app.compile.parse;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;

public interface MagmaVisitor extends Visitor {
    @Override
    default Stream<Parser> streamParsers(State state) {
        return Streams.apply(
                new BlockParser(state),
                new ImplementationParser(state),
                new VariableParser(state)
        );
    }
}
