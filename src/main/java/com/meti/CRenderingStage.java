package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CRenderingStage {
    private final String name;
    private final String type;
    private final int value;

    public CRenderingStage(String name, String type, int value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String render() throws CompileException {
        return renderFunctionHeader(type) + renderTree(new Block(List.of(new Return(new IntegerNode(value)))));
    }

    private String renderFunctionHeader(String input) {
        String typeString;
        if (input.equals("I16")) {
            typeString = "int";
        } else {
            typeString = "unsigned int";
        }
        return typeString + " " + name + "()";
    }

    private Node renderNodeChildren(Node value) throws CompileException {
        var types = value.stream(Attribute.Group.Node).collect(Collectors.toList());
        var current = value;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNode();
            var childOutput = renderTree(child);
            var childAttribute = new NodeAttribute(new Content(childOutput));
            current = current.with(Attribute.Type.Value, childAttribute);
        }
        return current;
    }

    private Node renderNodesChildren(Node value) throws CompileException {
        var types = value.stream(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = value;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNodeStream();
            var childAttribute = renderStream(child);
            current = current.with(Attribute.Type.Value, childAttribute);
        }
        return current;
    }

    private Attribute renderStream(Stream<Node> child) throws AttributeException {
        try {
            return new NodesAttribute(child.map(this::renderTree)
                    .map(Content::new)
                    .foldRight(new ArrayList<>(), (collection, node) -> {
                        collection.add(node);
                        return collection;
                    }));
        } catch (StreamException e) {
            throw new AttributeException("Cannot attribute with list of nodes.", e);
        }
    }

    private String renderTree(Node value) throws CompileException {
        var withNode = renderNodeChildren(value);
        var withNodes = renderNodesChildren(withNode);
        return new CRenderer(withNodes).render()
                .orElseThrow(() -> new CompileException("Cannot render: " + withNodes));
    }
}
