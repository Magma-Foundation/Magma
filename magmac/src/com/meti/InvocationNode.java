package com.meti;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public record InvocationNode(Node caller, List<Node> arguments) {
    Optional<String> render() {
        var argumentString = arguments()
                .stream()
                .map(Node::findValue)
                .collect(Collectors.joining(", "));

        return Optional.of("%s(%s)".formatted(caller.findValue(), String.join(", ", argumentString)));
    }

    public InvocationNode withCaller(Node caller) {
        return new InvocationNode(caller, arguments);
    }

    public InvocationNode withArguments(List<Node> arguments) {
        return new InvocationNode(caller, arguments);
    }

    public Optional<InvocationNode> mapCaller(Function<Node, Optional<Node>> mapper) {
        return mapper.apply(caller).map(applied -> new InvocationNode(applied, arguments));
    }

    public Optional<InvocationNode> mapArguments(Function<List<Node>, Optional<List<Node>>> mapper) {
        return mapper.apply(arguments).map(applied -> new InvocationNode(caller, applied));
    }
}