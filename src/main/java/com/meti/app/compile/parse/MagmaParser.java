package com.meti.app.compile.parse;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;

public interface MagmaParser extends VisitationStage<Parser> {
    @Override
    default Stream<Parser> streamVisitors(State state) {
        return Streams.apply(
                new BlockVisitor(state),
                new ImplementationVisitor(state),
                new VariableVisitor(state)
        );
    }
}
