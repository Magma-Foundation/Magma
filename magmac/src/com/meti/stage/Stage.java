package com.meti.stage;

import com.meti.Tuple;
import com.meti.node.Node;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public abstract class Stage<I, O> {
    public abstract Optional<Node> onEnter(I value);

    public Optional<List<Node>> applyToNodeList(List<Node> arguments, Stack<Node> b) {
        var list = arguments.stream()
                .map(node -> applyToNode(node, b))
                .toList();

        if (list.stream().anyMatch(Optional::isEmpty)) return Optional.empty();

        var flattened = list.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        return Optional.of(flattened);
    }

    public abstract Optional<Node> applyToNode(Node node, Stack<Node> b);

    public Optional<O> apply(I root) {
        return applyWithStack(root, new Stack<>());
    }

    protected Optional<O> applyWithStack(I root, Stack<Node> stack) {
        return onEnter(root)
                .map(node -> {
                    stack.add(node);
                    return new Tuple<>(node, stack);
                })
                .map(node -> new Tuple<>(node.a()
                        .mapNodes(node1 -> applyToNode(node1, node.b()))
                        .orElse(node.a()), stack))
                .map(node -> new Tuple<>(node.a()
                        .mapNodeLists(arguments -> applyToNodeList(arguments, node.b()))
                        .orElse(node.a()), stack))
                .flatMap((Tuple<Node, Stack<Node>> node2) -> {
                    var popped = node2.b();
                    popped.pop();
                    return onExit(node2.a(), popped);
                });
    }

    protected abstract Optional<O> onExit(Node node, Stack<Node> b);
}
