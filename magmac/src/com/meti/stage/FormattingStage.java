package com.meti.stage;

import com.meti.java.ClassLexer;
import com.meti.node.IntAttribute;
import com.meti.node.MapNode;
import com.meti.node.Node;
import com.meti.node.NodeAttribute;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FormattingStage extends TransformingStage {
    @Override
    public Optional<Node> onEnter(Node value) {
        return Optional.of(value);
    }

    @Override
    protected Optional<Node> onExit(Node node) {
        if (node.is("class")) {
            return Optional.of(node.mapNodeList(ClassLexer.BODY, FormattingStage::wrapInStatements));
        }

        if(node.is("method")) {
            return Optional.of(node.mapNodeList("body", FormattingStage::wrapInStatements));
        }

        return Optional.of(node);
    }

    private static List<Node> wrapInStatements(List<Node> list) {
        return list.stream()
                .<Node>map(FormattingStage::getMapNode)
                .collect(Collectors.toList());
    }

    private static MapNode getMapNode(Node element) {
        return new MapNode("statement", Map.of(
                "indent", new IntAttribute(1),
                "value", new NodeAttribute(element)
        ));
    }
}
