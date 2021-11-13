package com.meti;

import java.util.ArrayList;
import java.util.Collections;
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

    private String getString(Node field) throws CompileException {
        var dependents = renderDependents(field);
        return new CFieldRenderer(dependents)
                .render()
                .orElseThrow(() -> new CompileException("Cannot render field: " + field));
    }

    private Node parseHeader(String input, Node parameters) {
        return input.equals("I16")
                ? Primitive.I16.asField(name, parameters)
                : Primitive.U16.asField(name, parameters);
    }

    public String render() throws CompileException {
        var header = parseHeader(type, new Sequence(Collections.emptyList()));
        return getString(header) + renderNodeTree(new Block(List.of(new Return(new IntegerNode(value)))));
    }

    private Node renderDependents(Node value) throws CompileException {
        var withNode = renderNodeChildren(value);
        return renderNodesChildren(withNode);
    }

    private Node renderNodeChildren(Node value) throws CompileException {
        var types = value.stream(Attribute.Group.Node).collect(Collectors.toList());
        var current = value;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNode();
            var childOutput = renderNodeTree(child);
            var childAttribute = new NodeAttribute(new Content(childOutput));
            current = current.with(type, childAttribute);
        }
        return current;
    }

    private String renderNodeTree(Node value) throws CompileException {
        var withNodes = renderDependents(value);
        return new CNodeRenderer(withNodes).render()
                .orElseThrow(() -> new CompileException("Cannot render: " + withNodes));
    }

    private Node renderNodesChildren(Node value) throws CompileException {
        var types = value.stream(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = value;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNodeStream();
            var childAttribute = renderStream(child);
            current = current.with(type, childAttribute);
        }
        return current;
    }

    private Attribute renderStream(Stream<Node> child) throws AttributeException {
        try {
            return new NodesAttribute(child.map(this::renderNodeTree)
                    .map(Content::new)
                    .foldRight(new ArrayList<>(), (collection, node) -> {
                        collection.add(node);
                        return collection;
                    }));
        } catch (StreamException e) {
            throw new AttributeException("Cannot attribute with list of nodes.", e);
        }
    }
}
