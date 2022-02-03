package com.meti.app.compile.parse;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.feature.function.ImplementationParser;
import com.meti.app.compile.feature.function.InvocationParser;
import com.meti.app.compile.feature.util.LineParser;

public interface MagmaParsingStageMixin extends VisitationStage<Modifier> {
    @Override
    default Stream<Modifier> streamVisitors(State state) {
        return Streams.apply(
                new BinaryParser(state),
                new BlockVisitor(state),
                new BooleanParser(state),
                new ImplementationParser(state),
                new InvocationParser(state),
                new LineParser(state),
                new VariableVisitor(state));
    }
}
