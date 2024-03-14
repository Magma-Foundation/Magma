package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.meti.collect.option.Some.Some;

public record BlockNode(int indent, List<? extends Node> children) implements Node {
    @Override
    public Option<List<? extends Node>> findChildren() {
        return Some(children);
    }

    @Override
    public Option<Node> withChildren(List<? extends Node> children) {
        return Some(new BlockNode(indent, children));
    }

    @Override
    public Option<String> render() {
        return Some(children()
                .stream()
                .map(Node::render)
                .map(output -> output.map(Optional::of).orElse(Optional.empty()))
                .flatMap(Optional::stream)
                .collect(Collectors.joining("", "{\n", "\t".repeat(indent()) + "}\n")));
    }
}