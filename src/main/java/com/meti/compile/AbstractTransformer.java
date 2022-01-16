package com.meti.compile;

import com.meti.collect.Stream;
import com.meti.collect.StreamException;
import com.meti.collect.Streams;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.node.Node;

public abstract class AbstractTransformer {
    protected abstract Stream<Transformer> stream(Node node);

    Node transformAST(Node node) throws CompileException {
        var withNodeGroup = transformNodeGroup(node);
        var withNodesGroup = transformNodesGroup(withNodeGroup);
        var withDeclarationGroup = transformDeclarationGroup(withNodesGroup);
        return transformNode(withDeclarationGroup);
    }

    private Node transformDeclarationGroup(Node withNodesGroup) throws CompileException {
        try {
            return withNodesGroup.apply1(Attribute.Group.Declaration)
                    .foldRight(withNodesGroup, this::transformDeclarationGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node transformDeclarationGroup(Node root, Attribute.Type type) throws CompileException {
        var oldIdentity = root.apply(type).asNode();
        if (oldIdentity.is(Node.Type.ValuedField)) {
            var oldValue = oldIdentity.apply(Attribute.Type.Value).asNode();
            var newValue = transformAST(oldValue);
            var newValueAttribute = new NodeAttribute(newValue);
            var newIdentity = oldIdentity.with(Attribute.Type.Value, newValueAttribute);
            var newIdentityAttribute = new NodeAttribute(newIdentity);
            return root.with(type, newIdentityAttribute);
        } else {
            return root;
        }
    }

    private Node transformNode(Node node) throws CompileException {
        try {
            return stream(node)
                    .map(Transformer::transform)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(node);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node transformNodeAttribute(Node current, Attribute.Type type) throws CompileException {
        var previousAttribute = current.apply(type);
        var previous = previousAttribute.asNode();
        var next = transformAST(previous);
        var nextAttribute = new NodeAttribute(next);
        return current.with(type, nextAttribute);
    }

    private Node transformNodeGroup(Node node) throws CompileException {
        try {
            return node.apply1(Attribute.Group.Node).foldRight(node, this::transformNodeAttribute);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node transformNodesAttribute(Node current, Attribute.Type type) throws CompileException {
        try {
            return current.with(type, current.apply(type)
                    .asStreamOfNodes1()
                    .map(AbstractTransformer.this::transformAST)
                    .foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add)
                    .complete());
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node transformNodesGroup(Node withChild) throws CompileException {
        try {
            return withChild.apply1(Attribute.Group.Nodes)
                    .foldRight(withChild, this::transformNodesAttribute);
        } catch (StreamException e) {
            throw new TransformationException(e);
        }
    }
}
