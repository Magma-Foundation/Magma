package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public record InvocationNode(Node caller, List<Node> arguments) implements Node {
    @Override
    public Optional<String> render() {
        var argumentString = arguments()
                .stream()
                .map(node -> node.findValue().orElseThrow())
                .collect(Collectors.joining(", "));

        return Optional.of("%s(%s)".formatted(caller.findValue().orElseThrow(), String.join(", ", argumentString)));
    }

    @Override
    public Optional<Node> mapNodes(Function<Node, Optional<Node>> mapper) {
        return mapper.apply(caller).map(applied -> new InvocationNode(applied, arguments));
    }

    @Override
    public Optional<Node> mapNodeLists(Function<List<Node>, Optional<List<Node>>> mapper) {
        return mapper.apply(arguments).map(applied -> new InvocationNode(caller, applied));
    }
}