package com.meti.compile;

import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.common.Line;
import com.meti.compile.common.block.Block;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record CMagmaTransformer() {
    Node transform(Node node) throws CompileException {
        var withNodeGroup = transformNodeGroup(node);
        var withNodesGroup = transformNodesGroup(withNodeGroup);
        var withDeclarationGroup = transformDeclarationGroup(withNodesGroup);
        return transformBoolean(withDeclarationGroup)
                .or(transformBlockOptionally(withDeclarationGroup))
                .orElse(withDeclarationGroup);
    }

    private Node transformBlock(Node root) throws CompileException {
        try {
            return root.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .map(child -> child.is(Node.Type.Binary) ? new Line(child) : child)
                    .foldRight(new Block.Builder(), Block.Builder::add)
                    .complete();
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Option<Node> transformBlockOptionally(Node root) throws CompileException {
        if (root.is(Node.Type.Block)) {
            return new Some<>(transformBlock(root));
        } else {
            return new None<>();
        }
    }

    private Option<Node> transformBoolean(Node node) throws TransformationException {
        if (node.is(Node.Type.Boolean)) {
            try {
                var value = node.apply(Attribute.Type.Value);
                var state = value.asBoolean();
                return new Some<>(new IntegerNode(state ? 1 : 0));
            } catch (AttributeException e) {
                throw new TransformationException(e);
            }
        }
        return new None<>();
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
            var newValue = transform(oldValue);
            var newValueAttribute = new NodeAttribute(newValue);
            var newIdentity = oldIdentity.with(Attribute.Type.Value, newValueAttribute);
            var newIdentityAttribute = new NodeAttribute(newIdentity);
            return root.with(type, newIdentityAttribute);
        } else {
            return root;
        }
    }

    private Node transformNodeAttribute(Node current, Attribute.Type type) throws CompileException {
        var previousAttribute = current.apply(type);
        var previous = previousAttribute.asNode();
        var next = transform(previous);
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
                    .map(CMagmaTransformer.this::transform)
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
