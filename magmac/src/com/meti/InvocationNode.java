package com.meti;

import com.meti.node.Attribute;
import com.meti.node.IntAttribute;
import com.meti.node.Node;
import com.meti.node.StringAttribute;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public record InvocationNode(Node caller, List<Node> arguments) implements Node {
    @Override
    public Optional<String> render() {
        var argumentString = arguments()
                .stream()
                .map(node -> node.apply("value").flatMap(Attribute::asString).orElseThrow())
                .collect(Collectors.joining(", "));

        return Optional.of("%s(%s)".formatted(caller.apply("value").flatMap(Attribute::asString).orElseThrow(), String.join(", ", argumentString)));
    }

    @Override
    public Optional<Node> mapNodes(Function<Node, Optional<Node>> mapper) {
        return mapper.apply(caller).map(applied -> new InvocationNode(applied, arguments));
    }

    @Override
    public Optional<Node> mapNodeLists(Function<List<Node>, Optional<List<Node>>> mapper) {
        return mapper.apply(arguments).map(applied -> new InvocationNode(caller, applied));
    }

    @Override
    public Optional<Attribute> apply(String name) {
        if(name.equals("value")) return findValue().map(StringAttribute::new);
        if(name.equals("indent")) return findIndent().map(IntAttribute::new);
        return Optional.empty();
    }

    private Optional<String> findValue() {
        throw new UnsupportedOperationException();
    }

    private Optional<Integer> findIndent() {
        throw new UnsupportedOperationException();
    }
}