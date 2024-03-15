package com.meti.compile.procedure;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.collect.option.Some.Some;

public record InvocationNode(Node caller, List<? extends Node> children) implements Node {
    public InvocationNode(Node caller) {
        this(caller, new ArrayList<>());
    }

    @Override
    public Option<List<? extends Node>> findChildren() {
        return Some(children);
    }

    @Override
    public Option<Node> findValueAsNode() {
        return Some(caller);
    }

    @Override
    public Option<Node> withValue(Node value) {
        return Some(new InvocationNode(value, children));
    }

    @Override
    public Option<Node> withChildren(List<? extends Node> children) {
        return Some(new InvocationNode(caller, children));
    }

    @Override
    public Option<String> render() {
        var args = children()
                .stream()
                .map(Node::render)
                .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                .flatMap(Optional::stream)
                .collect(Collectors.joining(", ", "(", ")"));

        return Some(caller.render().orElse("") + args);
    }

    @Override
    public boolean is(String name) {
        throw new UnsupportedOperationException();
    }
}