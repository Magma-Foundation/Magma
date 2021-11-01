package com.meti.app.compile;

import com.meti.api.StreamException;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodeListAttribute;
import com.meti.app.magma.MagmaLexer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record MagmaLexingStage(String root) {
    public static Node attachDependents(Node node) throws CompileException {
        var withNodes = attachNodes(node);
        var withNodeLists = withNodes.stream(Attribute.Group.NodeList).collect(Collectors.toList());
        var current = withNodes;
        for (Attribute.Type type : withNodeLists) {
            List<? extends Node> children = getChildren(current, type);
            current = current.with(type, new NodeListAttribute(children));
        }
        return current;
    }

    private static ArrayList<Node> getChildren(Node current, Attribute.Type type) throws CompileException {
        try {
            return current.apply(type).asNodeStream()
                    .map(MagmaLexingStage::parse)
                    .foldRight(new ArrayList<>(), (nodes, node1) -> {
                        nodes.add(node1);
                        return nodes;
                    });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private static Node attachNodes(Node node) throws CompileException {
        var children = node.stream(Attribute.Group.Node).collect(Collectors.toList());
        var current = node;
        for (Attribute.Type type : children) {
            var oldChild = current.apply(type).asNode();
            var newChild = parse(oldChild);
            current = current.with(type, new NodeAttribute(attachDependents(newChild)));
        }
        return current;
    }

    public static Node parse(Node child) throws CompileException {
        Node newChild;
        if (child.is(Node.Type.Content)) {
            var input = child.apply(Attribute.Type.Value).asInput();
            newChild = new MagmaLexer(input).process();
        } else {
            newChild = child;
        }
        return attachDependents(newChild);
    }

    Node lex() throws CompileException {
        return parse(new Content(new Input(root)));
    }
}