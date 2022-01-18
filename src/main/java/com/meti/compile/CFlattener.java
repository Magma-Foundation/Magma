package com.meti.compile;

import com.meti.api.collect.JavaList;
import com.meti.api.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute1;
import com.meti.compile.cache.Cache;
import com.meti.compile.cache.CacheBuilder;
import com.meti.compile.node.Node;

public class CFlattener extends AbstractStage {
    @Override
    protected Node transformDefinition(Node definition) throws CompileException {
        return apply(definition);
    }

    @Override
    public Node apply(Node node) throws CompileException {
        try {
            if (node.is(Node.Type.Cache)) {
                var innerCache = node.apply(Attribute.Type.Value).asNode();
                var withChildren = node.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .map(this::flattenCache)
                        .flatMap(JavaList::stream)
                        .foldRight(new JavaList<Node>(), JavaList::add);
                if (innerCache.is(Node.Type.Cache)) {
                    var innerValue = innerCache.apply(Attribute.Type.Value).asNode();
                    var withSubChildren = innerCache.apply(Attribute.Type.Children)
                            .asStreamOfNodes1()
                            .foldRight(withChildren, JavaList::add);
                    return new Cache(innerValue, withSubChildren);
                } else {
                    return new Cache(innerCache, withChildren);
                }
            }

            var prototype = new CacheBuilder<>(node);
            var withNodeAttributes = flattenNodeAttributes(node, prototype);
            var withNodesAttributes = flattenNodesAttributes(node, withNodeAttributes);
            return withNodesAttributes.build(value -> value);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private JavaList<Node> flattenCache(Node parent) throws AttributeException, StreamException {
        if (parent.is(Node.Type.Cache)) {
            var value = parent.apply(Attribute.Type.Value).asNode();
            return parent.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .foldRight(JavaList.apply(value), JavaList::add);
        } else {
            return JavaList.apply(parent);
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
}
