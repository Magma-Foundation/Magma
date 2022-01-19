package com.meti.app.compile.node;

import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record PrimitiveLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        try {
            return Streams.apply(Primitive.values())
                    .filter(value -> text.equalsSlice(value.name()))
                    .headOptionally()
                    .map(value -> value);
        } catch (StreamException e) {
            return new None<>();
        }
    }
}
