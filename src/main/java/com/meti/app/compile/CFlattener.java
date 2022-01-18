package com.meti.app.compile;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.attribute.NodesAttribute1;
import com.meti.app.compile.cache.Cache;
import com.meti.app.compile.cache.CacheBuilder;
import com.meti.app.compile.node.Node;

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
                        .flatMap(List::stream)
                        .foldRight(List.<Node>createList(), List::add);
                if (innerCache.is(Node.Type.Cache)) {
                    var innerValue = innerCache.apply(Attribute.Type.Value).asNode();
                    var withSubChildren = innerCache.apply(Attribute.Type.Children)
                            .asStreamOfNodes1()
                            .foldRight(withChildren, List::add);
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

    private List<Node> flattenCache(Node parent) throws AttributeException, StreamException {
        if (parent.is(Node.Type.Cache)) {
            var value = parent.apply(Attribute.Type.Value).asNode();
            return parent.apply(Attribute.Type.Children)
                    .asStreamOfNodes1()
                    .foldRight(List.apply(value), List::add);
        } else {
            return List.apply(parent);
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

    private CacheBuilder<List<Node>> flattenChildren(Node root, Attribute.Type type) throws StreamException, AttributeException {
        return root.apply(type)
                .asStreamOfNodes1()
                .map(CacheBuilder::apply)
                .foldRight(new CacheBuilder<>(List.createList()),
                        (previous, next) -> previous.append(next, List::add));
    }
}
