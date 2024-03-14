package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.Some.Some;

public record BlockNode(int indent, List<Node> stringStream) implements Node {
    @Override
    public Option<String> render() {
        return Some(stringStream()
                .stream()
                .map(Node::render)
                .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                .flatMap(Optional::stream)
                .collect(Collectors.joining("", "{\n", "\t".repeat(indent()) + "}\n")));
    }
}