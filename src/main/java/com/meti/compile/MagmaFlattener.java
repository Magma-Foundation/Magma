package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.cache.CacheBuilder;
import com.meti.compile.node.Node;

public class MagmaFlattener extends AbstractStage {
    @Override
    public Node apply(Node node) throws CompileException {
        try {
            var prototype = new CacheBuilder<>(node);
            var withNodeAttributes = flattenNodeAttributes(node, prototype);
            var withNodesAttributes = flattenNodesAttributes(node, withNodeAttributes);
            return withNodesAttributes.build(value -> value);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private CacheBuilder<Node> flattenNodeAttributes(Node node, CacheBuilder<Node> previous) throws StreamException, AttributeException {
        return node.apply1(Attribute.Group.Node).foldRight(previous, (builder, type) -> {
            var child = node.apply(type).asNode();
            var childCache = CacheBuilder.apply(child);
            return builder.append(childCache, (current, next) -> current.with(type, new NodeAttribute(next)));
        });
    }

    private CacheBuilder<Node> flattenNodesAttributes(Node node, CacheBuilder<Node> previous) throws CompileException {
        try {
            return node.apply1(Attribute.Group.Nodes).foldRight(previous, (builder, type) -> attachChildren(node, builder, type));
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private CacheBuilder<Node> attachChildren(Node root, CacheBuilder<Node> previous, Attribute.Type type) throws CompileException {
        try {
            var builder = flattenChildren(root, type);
            var other = builder.map(NodesAttribute1::new);
            return previous.append(other, (current, children) -> current.with(type, children));
        } catch (StreamException | AttributeException e) {
            throw new CompileException(e);
        }
    }

    private CacheBuilder<JavaList<Node>> flattenChildren(Node root, Attribute.Type type) throws StreamException, AttributeException {
        return root.apply(type)
                .asStreamOfNodes1()
                .map(CacheBuilder::apply)
                .foldRight(new CacheBuilder<>(new JavaList<>()),
                        (previous, next) -> previous.append(next, JavaList::add));
    }

    @Override
    protected Node transformDefinition(Node definition) {
        return definition;
    }
}
