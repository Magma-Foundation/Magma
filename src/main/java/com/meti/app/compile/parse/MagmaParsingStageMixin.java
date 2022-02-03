package com.meti.app.compile.parse;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.feature.function.ImplementationVisitor;
import com.meti.app.compile.feature.function.InvocationParser;

public interface MagmaParsingStageMixin extends VisitationStage<Parser> {
    @Override
    default Stream<Parser> streamVisitors(State state) {
        return Streams.apply(
                new BinaryParser(state),
                new BlockVisitor(state),
                new BooleanParser(state),
                new ImplementationVisitor(state),
                new InvocationParser(state),
                new VariableVisitor(state));
    }
}
