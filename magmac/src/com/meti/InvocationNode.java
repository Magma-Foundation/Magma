package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.Some.Some;

public record InvocationNode(List<? extends Node> children) implements Node {
    @Override
    public Option<List<? extends Node>> findChildren() {
        return Some(children);
    }

    @Override
    public Option<Node> withChildren(List<? extends Node> children) {
        return Some(new InvocationNode(children));
    }

    @Override
    public Option<String> render() {
        var args = children()
                .stream()
                .map(Node::render)
                .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                .flatMap(Optional::stream)
                .collect(Collectors.joining(", ", "(", ")"));

        return Some("Paths.get" + args);
    }
}