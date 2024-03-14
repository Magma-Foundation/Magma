package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.Some.Some;

public record InvocationNode(List<Node> list) implements Node {
    @Override
    public Option<String> render() {
        var args = list()
                .stream()
                .map(Node::render)
                .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                .flatMap(Optional::stream)
                .collect(Collectors.joining(", ", "(", ")"));

        return Some("Paths.get" + args);
    }
}