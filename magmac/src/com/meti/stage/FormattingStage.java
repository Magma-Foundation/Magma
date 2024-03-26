package com.meti.stage;

import com.meti.java.ClassLexer;
import com.meti.node.IntAttribute;
import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.NodeAttribute;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class FormattingStage extends TransformingStage {
    @Override
    public Optional<Node> onEnter(Node value) {
        return Optional.of(value);
    }

    @Override
    protected Optional<Node> onExit(Node node, Stack<Node> b) {
        var indent = b.size() + 1;
        if (node.is("class")) {
            return Optional.of(node.mapNodeList(ClassLexer.BODY, list -> wrapInStatements(list, indent)));
        }

        if(node.is("method")) {
            return Optional.of(node.mapNodeList("body", list -> wrapInStatements(list, indent))
                    .replace("indent", new IntAttribute(b.size())));
        }

        return Optional.of(node);
    }

    private static List<Node> wrapInStatements(List<Node> list, int indent) {
        return list.stream()
                .<Node>map(element -> getMapNode(element, indent))
                .collect(Collectors.toList());
    }

    private static MapNode getMapNode(Node element, int indent) {
        return new MapNode("statement", Map.of(
                "indent", new IntAttribute(indent),
                "value", new NodeAttribute(element)
        ));
    }
}
