package com.meti.compile;

import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.common.EmptyField;
import com.meti.compile.common.Line;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Collections;

public record CMagmaTransformer() {
    private static Option<Node> fixAbstraction(Node node) throws CompileException {
        return new Some<>(node)
                .filter(value -> value.is(Node.Type.Abstraction))
                .map(value -> isExternal(value) ? fixFunction(node) : new EmptyNode());
    }

    private static Option<Node> fixBlock(Node node) throws CompileException {
        return new Some<>(node)
                .filter(value -> value.is(Node.Type.Block))
                .map(CMagmaTransformer::fixChildren);
    }

    private static Node fixChildren(Node node) throws AttributeException, TransformationException {
        try {
            var oldChildren = wrapChildren(node);
            return node.with(Attribute.Type.Children, oldChildren);
        } catch (StreamException e) {
            throw new TransformationException(e);
        }
    }

    static Node fixFunction(Node node) throws AttributeException, ResolutionException, TransformationException {
        var identity = node.apply(Attribute.Type.Identity).asNode();

        var oldReturnType = identity.apply(Attribute.Type.Type).asNode();
        var newReturnType = resolveValue(node, oldReturnType);

        var newIdentity = identity.with(Attribute.Type.Type, new NodeAttribute(newReturnType));
        var withIdentity = node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));

        try {
            var oldParameters = withIdentity.apply(Attribute.Type.Parameters)
                    .asStreamOfNodes1()
                    .map(CMagmaTransformer::fixParameter)
                    .foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add)
                    .complete();
            return withIdentity.with(Attribute.Type.Parameters, oldParameters);
        } catch (StreamException e) {
            throw new TransformationException(e);
        }
    }

    private static Option<Node> fixImplementation(Node node) throws CompileException {
        return new Some<>(node)
                .filter(value -> value.is(Node.Type.Implementation))
                .map(CMagmaTransformer::fixFunction);
    }

    private static Node fixParameter(Node parameter) throws AttributeException {
        var oldType = parameter.apply(Attribute.Type.Type).asNode();
        if (oldType.is(Node.Type.Primitive) && oldType.apply(Attribute.Type.Name).asText().compute().equals("Bool")) {
            var newType = new IntegerType(true, 16);
            var attribute = new NodeAttribute(newType);
            return parameter.with(Attribute.Type.Type, attribute);
        } else {
            return parameter;
        }
    }

    private static boolean isExternal(Node node) throws AttributeException {
        return node.apply(Attribute.Type.Identity)
                .asNode()
                .apply(Attribute.Type.Flags)
                .asStreamOfFlags()
                .noneMatch(value -> value == EmptyField.Flag.Extern);
    }

    private static Node resolveBlock(Node root) throws AttributeException, ResolutionException {
        try {
            return root.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .filter(value -> value.is(Node.Type.Return))
                    .first()
                    .orElse(Primitive.Void);
        } catch (StreamException e) {
            throw new ResolutionException(e);
        }
    }

    private static Node resolveNode(Node root) throws AttributeException, ResolutionException {
        if (root.is(Node.Type.Block)) {
            return resolveBlock(root);
        } else if (root.is(Node.Type.Return)) {
            return resolveReturn(root);
        } else if (root.is(Node.Type.Integer)) {
            return new IntegerType(true, 16);
        } else {
            throw new ResolutionException("Cannot resolve: " + root);
        }
    }

    private static Node resolveReturn(Node root) throws AttributeException, ResolutionException {
        var apply = root.apply(Attribute.Type.Value);
        var value = apply.asNode();
        return resolveNode(value);
    }

    private static Node resolveValue(Node node, Node oldReturnType) throws AttributeException, ResolutionException {
        if (oldReturnType.is(Node.Type.Implicit)) {
            var apply = node.apply(Attribute.Type.Value);
            var value = apply.asNode();
            return resolveNode(value);
        } else {
            return oldReturnType;
        }
    }

    private static Option<Node> toBoolean(Node node) throws AttributeException {
        if (node.is(Node.Type.Boolean)) {
            var apply = node.apply(Attribute.Type.Value);
            var value = apply.asBoolean() ? 1 : 0;
            var integerNode = new IntegerNode(value);
            return new Some<>(integerNode);
        }
        return new None<>();
    }

    private static Attribute wrapChildren(Node node) throws StreamException, AttributeException {
        return node.apply(Attribute.Type.Children)
                .asStreamOfNodes1()
                .map(CMagmaTransformer::wrap)
                .foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add)
                .complete();
    }

    private static Node wrap(Node oldChild) {
        if (oldChild.is(Node.Type.Abstraction) || oldChild.is(Node.Type.Implementation)) {
            return new Cache(oldChild, Collections.emptyList());
        } else if (oldChild.is(Node.Type.Invocation) || oldChild.is(Node.Type.Binary)) {
            return new Line(oldChild);
        } else {
            return oldChild;
        }
    }

    Node resolveField(Node field) throws CompileException {
        if (field.is(Node.Type.ValuedField)) {
            return transformNodeAttribute(field, Attribute.Type.Value);
        } else {
            return field;
        }
    }

    private Node resolveFieldAttribute(Node current, Attribute.Type type) throws CompileException {
        var oldField = current.apply(type).asNode();
        var newField = resolveField(oldField);
        var attribute = new NodeAttribute(newField);
        return current.with(type, attribute);
    }

    private Node resolveFieldStage(Node root) throws CompileException {
        try {
            return root.apply1(Attribute.Group.Declaration)
                    .foldRight(root, this::resolveFieldAttribute);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node resolveNodesAttributes(Node root) throws CompileException {
        try {
            return root.apply1(Attribute.Group.Nodes).foldRight(root, (node, type) -> root.with(type, node.apply(type)
                    .asStreamOfNodes1()
                    .map(this::transformStage)
                    .foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add)
                    .complete()));
        } catch (Exception e) {
            throw new CompileException(e);
        }
    }

    private Node transformNodeAttribute(Node node, Attribute.Type type) throws CompileException {
        var apply = node.apply(type);
        var oldChild = apply.asNode();
        var newChild = this.transformStage(oldChild);
        var attribute = new NodeAttribute(newChild);
        return node.with(type, attribute);
    }

    private Node transformNodeAttributes(Node root) throws CompileException {
        try {
            return root.apply1(Attribute.Group.Node).foldRight(root, this::transformNodeAttribute);
        } catch (StreamException e) {
            throw new TransformationException(e);
        }
    }

    Node transformStage(Node node) throws CompileException {
        var resolved = toBoolean(node)
                .or(() -> fixImplementation(node))
                .or(() -> fixAbstraction(node))
                .or(() -> fixBlock(node))
                .orElse(node);
        var withNodes = transformNodeAttributes(resolved);
        var withNodesAttributes = resolveNodesAttributes(withNodes);
        return resolveFieldStage(withNodesAttributes);
    }
}
