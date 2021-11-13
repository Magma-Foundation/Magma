package com.meti;

import java.util.ArrayList;
import java.util.stream.Collectors;

public record CRenderingStage(Node root) {
    public String render() throws CompileException {
        return renderNodeTree(root);
    }

    private Node renderDependents(Node value) throws CompileException {
        var withNode = renderNodeChildren(value);
        var withNodes = renderNodesChildren(withNode);
        return withField(withNodes);
    }

    private String renderField(Node field) throws CompileException {
        var dependents = renderDependents(field);
        return new CFieldRenderer(dependents)
                .render()
                .orElseThrow(() -> new CompileException("Cannot render field: " + field));
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

    private Node withField(Node withNodes) throws CompileException {
        var types = withNodes.stream(Attribute.Group.Field).collect(Collectors.toList());
        var current = withNodes;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNode();
            var childOutput = renderField(child);
            var childAttribute = new NodeAttribute(new Content(childOutput));
            current = current.with(type, childAttribute);
        }
        return current;
    }
}
