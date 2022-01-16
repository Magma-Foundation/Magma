package com.meti.compile;

import com.meti.collect.StreamException;
import com.meti.compile.attribute.*;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public record CMagmaTransformer(ArrayList<Node> lexed) {
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

    static Node resolveField(Node field) throws CompileException {
        if (field.is(Node.Type.ValuedField)) {
            var oldValue = field.apply(Attribute.Type.Value).asNode();
            var newValue = resolveStage(oldValue);
            return field.with(Attribute.Type.Value, new NodeAttribute(newValue));
        } else {
            return field;
        }
    }

    private static Node resolveFieldStage(Node root) throws CompileException {
        try {
            return root.apply1(Attribute.Group.Declaration)
                    .foldRight(root, CMagmaTransformer::resolveFieldAttribute);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private static Node resolveFieldAttribute(Node current, Attribute.Type type) throws CompileException {
        var oldField = current.apply(type).asNode();
        var newField = resolveField(oldField);
        var attribute = new NodeAttribute(newField);
        return current.with(type, attribute);
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

    private static Node resolveNodeAttributes(Node root) throws CompileException {
        var list = root.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = root;
        var cached = new ArrayList<Node>();

        for (Attribute.Type type : list) {
            var oldChild = current.apply(type).asNode();
            var newChild = resolveStage(oldChild);

            if (newChild.is(Node.Type.Cache)) {
                var internalChild = newChild.apply(Attribute.Type.Value).asNode();
                cached.addAll(newChild.apply(Attribute.Type.Children).asStreamOfNodes().collect(Collectors.toList()));
                current = current.with(type, new NodeAttribute(internalChild));
            } else {
                current = current.with(type, new NodeAttribute(newChild));
            }
        }

        if (cached.isEmpty()) {
            return current;
        } else {
            return new Cache(current, cached);
        }
    }

    private static Node resolveNodesAttributes(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Nodes).collect(Collectors.toList());
        var current = root;
        var cached = new ArrayList<Node>();

        for (Attribute.Type type : types) {
            var children = current.apply(type).asStreamOfNodes().collect(Collectors.toList());
            var newChildren = new ArrayList<Node>();
            for (Node oldChild : children) {
                var newChild = resolveStage(oldChild);

                if (newChild.is(Node.Type.Cache)) {
                    var internalChild = newChild.apply(Attribute.Type.Value).asNode();
                    if (internalChild.is(Node.Type.Implementation)) {
                        cached.add(internalChild);
                    }
                } else {
                    newChildren.add(newChild);
                }
            }
            current = current.with(type, new NodesAttribute(newChildren));
        }

        if (cached.isEmpty()) {
            return current;
        } else {
            return new Cache(current, cached);
        }
    }

    private static Node resolveReturn(Node root) throws AttributeException, ResolutionException {
        var apply = root.apply(Attribute.Type.Value);
        var value = apply.asNode();
        return resolveNode(value);
    }

    static Node resolveStage(Node node) throws CompileException {
        var resolved = toBoolean(node)
                .or(() -> fixImplementation(node))
                .or(() -> fixAbstraction(node))
                .or(() -> fixBlock(node))
                .orElse(node);
        var withNodes = resolveNodeAttributes(resolved);
        var withNodesAttributes = resolveNodesAttributes(withNodes);
        return resolveFieldStage(withNodesAttributes);
    }

    private static Node resolveValue(Node node, Node oldReturnType) throws AttributeException, ResolutionException {
        Node newReturnType;
        if (oldReturnType.is(Node.Type.Implicit)) {
            var value = node.apply(Attribute.Type.Value).asNode();
            newReturnType = resolveNode(value);
        } else {
            newReturnType = oldReturnType;
        }
        return newReturnType;
    }

    private static Option<Node> toBoolean(Node node) throws AttributeException {
        if (node.is(Node.Type.Boolean)) {
            return new Some<>(new IntegerNode(node.apply(Attribute.Type.Value).asBoolean() ? 1 : 0));
        }
        return new None<>();
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

    private static Attribute wrapChildren(Node node) throws StreamException, AttributeException {
        return node.apply(Attribute.Type.Children)
                .asStreamOfNodes1()
                .map(CMagmaTransformer::wrap)
                .foldRight(new NodesAttribute1.Builder(), NodesAttribute1.Builder::add)
                .complete();
    }

    public ArrayList<Node> getLexed() {
        return lexed;
    }

    ArrayList<Node> resolveStage() throws CompileException {
        var resolved = new ArrayList<Node>();
        for (Node node : getLexed()) {
            resolved.add(resolveStage(node));
        }
        return resolved;
    }
}
