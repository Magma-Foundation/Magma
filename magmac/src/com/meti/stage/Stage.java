package com.meti.stage;

import com.meti.node.Node;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Stage<I, O> {
    public abstract Optional<Node> onEnter(I value);

    public Optional<List<Node>> applyToNodeList(List<Node> arguments) {
        var list = arguments.stream()
                .map(this::applyToNode)
                .toList();

        if (list.stream().anyMatch(Optional::isEmpty)) return Optional.empty();

        var flattened = list.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        return Optional.of(flattened);
    }

    public Optional<Node> applyToNode(Node node) {
        return apply(createInput(node)).map(this::createOutput);
    }

    protected abstract Node createOutput(O o);

    protected abstract I createInput(Node node);

    public Optional<O> apply(I root) {
        return onEnter(root)
                .map(node -> node.mapNodes(this::applyToNode).orElse(node))
                .map(node -> node.mapNodeLists(this::applyToNodeList).orElse(node))
                .flatMap(this::onExit);
    }

    protected abstract Optional<O> onExit(Node node);
}
