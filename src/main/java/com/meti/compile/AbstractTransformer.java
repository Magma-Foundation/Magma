package com.meti.compile;

import com.meti.collect.EmptyStream;
import com.meti.collect.Stream;
import com.meti.collect.StreamException;
import com.meti.collect.Streams;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.node.Node;

public abstract class AbstractTransformer {
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return new EmptyStream<>();
    }

    protected Stream<Transformer> streamTypeTransformers(Node node) {
        return new EmptyStream<>();
    }

    private Node transformDeclarationGroup(Node root, Attribute.Type type) throws CompileException {
        var oldIdentity = root.apply(type).asNode();
        if (oldIdentity.is(Node.Type.ValuedField)) {
            var withType = transformTypeAttribute(oldIdentity, Attribute.Type.Type);
            var withValue = transformNodeAttribute(withType, Attribute.Type.Value);
            var newIdentityAttribute = new NodeAttribute(withValue);
            return root.with(type, newIdentityAttribute);
        } else {
            return root;
        }
    }

    private Node transformDeclarationGroup(Node withNodesGroup) throws CompileException {
        try {
            return withNodesGroup.apply1(Attribute.Group.Declaration)
                    .foldRight(withNodesGroup, this::transformDeclarationGroup);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node transformNode(Node node) throws CompileException {
        return transformUsingStreams(node, streamNodeTransformers(node));
    }

    Node transformNodeAST(Node node) throws CompileException {
        var withNodeGroup = transformNodeGroup(node);
        var withNodesGroup = transformNodesGroup(withNodeGroup);
        var withDeclarationGroup = transformDeclarationGroup(withNodesGroup);
        return transformNode(withDeclarationGroup);
    }

    private Node transformNodeAttribute(Node current, Attribute.Type type) throws CompileException {
        var previousAttribute = current.apply(type);
        var previous = previousAttribute.asNode();
        var next = transformNodeAST(previous);
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
                    .map(AbstractTransformer.this::transformNodeAST)
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

    private Node transformTypeAST(Node type) throws CompileException {
        return transformUsingStreams(type, streamTypeTransformers(type));
    }

    private Node transformTypeAttribute(Node oldIdentity, Attribute.Type type) throws CompileException {
        var oldType = oldIdentity.apply(type).asNode();
        var newType = transformTypeAST(oldType);
        var newTypeAttribute = new NodeAttribute(newType);
        return oldIdentity.with(type, newTypeAttribute);
    }

    private Node transformUsingStreams(Node node, Stream<Transformer> transformers) throws CompileException {
        try {
            return transformers.map(Transformer::transform)
                    .flatMap(Streams::optionally)
                    .first()
                    .orElse(node);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
