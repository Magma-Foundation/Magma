package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class CRenderer {
    private final String name;
    private final String type;
    private final int value;

    public CRenderer(String name, String type, int value) {
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

    private String renderNode(Node value) throws CompileException {
        try {
            return new ArrayStream<>(
                    new BlockRenderer(value),
                    new IntegerRenderer(value),
                    new ReturnRenderer(value))
                    .map(AbstractRenderer::render)
                    .flatMap(OptionStream::new)
                    .first()
                    .orElseThrow(() -> new CompileException("Cannot render: " + value));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
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
        return renderNode(withNodes);
    }
}
