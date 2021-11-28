package com.meti.app.stage;

import com.meti.api.stream.Stream;
import com.meti.api.stream.StreamException;
import com.meti.app.CompileException;
import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.attribute.NodeAttribute;
import com.meti.app.attribute.NodesAttribute;
import com.meti.app.node.Node;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class AbstractProcessingStage {
    protected final Node root;

    public AbstractProcessingStage(Node root) {
        this.root = root;
    }

    public Node process() throws CompileException {
        return processNodeTree(root);
    }

    protected abstract Node processNodeTree(Node value) throws CompileException;

    protected Node processDependents(Node value) throws CompileException {
        var withNode = processNode(value);
        var withNodes = processNodes(withNode);
        return processField(withNodes);
    }

    private Node processNode(Node value) throws CompileException {
        var types = value.stream(Attribute.Group.Node).collect(Collectors.toList());
        var current = value;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNode();
            var childOutput = processNodeTree(child);
            var childAttribute = new NodeAttribute(childOutput);
            current = current.with(type, childAttribute);
        }
        return current;
    }

    private Node processNodes(Node value) throws CompileException {
        var types = value.stream(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = value;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNodeStream();
            var childAttribute = processNodeStream(child);
            current = current.with(type, childAttribute);
        }
        return current;
    }

    private Node processField(Node withNodes) throws CompileException {
        var types = withNodes.stream(Attribute.Group.Field).collect(Collectors.toList());
        var current = withNodes;
        for (Attribute.Type type : types) {
            var child = current.apply(type).asNode();
            var childOutput = processFieldNode(child);
            var childAttribute = new NodeAttribute(childOutput);
            current = current.with(type, childAttribute);
        }
        return current;
    }

    private Attribute processNodeStream(Stream<? extends Node> child) throws AttributeException {
        try {
            return new NodesAttribute(child.map(this::processNodeTree)
                    .foldRight(new ArrayList<>(), (collection, node) -> {
                        collection.add(node);
                        return collection;
                    }));
        } catch (StreamException e) {
            throw new AttributeException("Cannot process stream of nodes nodes.", e);
        }
    }

    protected abstract Node processFieldNode(Node field) throws CompileException;
}
