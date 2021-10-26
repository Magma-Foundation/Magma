package com.meti.app.compile.node.output;

import com.meti.api.F1;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CompoundOutput(List<Output> children) implements Output {
    @Override
    public Option<String> asString() {
        if (children.isEmpty()) return new None<>();
        return new Some<>(children.stream()
                .map(Output::asString)
                .map(value -> value.map(Stream::of))
                .flatMap(value -> value.orElse(Stream.empty()))
                .collect(Collectors.joining()));
    }

    @Override
    public <E extends Exception> Output mapNodes(F1<Node, Output, E> mapper) throws E {
        var list = new ArrayList<Output>();
        for (var child : children) {
            list.add(child.asNode()
                    .map(mapper)
                    .orElse(child));
        }
        return new CompoundOutput(list);
    }
}
