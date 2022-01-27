package com.meti.app.compile.stage;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.cache.Cache;
import com.meti.app.compile.cache.CacheBuilder;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute1;

public class CFlattener extends AbstractStage {
    @Override
    protected Node afterTraversal(Node root) throws CompileException {
        try {
            if (root.is(Node.Type.Cache)) {
                var innerCache = root.apply(Attribute.Type.Value).asNode();
                var withChildren = root.apply(Attribute.Type.Children)
                        .asStreamOfNodes()
                        .map(this::flattenCache)
                        .flatMap(List::stream)
                        .foldRight(List.<Node>createList(), List::add);
                if (innerCache.is(Node.Type.Cache)) {
                    var innerValue = innerCache.apply(Attribute.Type.Value).asNode();
                    var withSubChildren = innerCache.apply(Attribute.Type.Children)
                            .asStreamOfNodes()
                            .foldRight(withChildren, List::add);
                    return new Cache(innerValue, withSubChildren);
                } else {
                    return new Cache(innerCache, withChildren);
                }
            }

            var prototype = new CacheBuilder<>(root);
            var withNodeAttributes = flattenNodeAttributes(root, prototype);
            var withNodesAttributes = flattenNodesAttributes(root, withNodeAttributes);
            return withNodesAttributes.build(value -> value);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private List<Node> flattenCache(Node parent) throws AttributeException, StreamException {
        if (parent.is(Node.Type.Cache)) {
            var value = parent.apply(Attribute.Type.Value).asNode();
            return parent.apply(Attribute.Type.Children)
                    .asStreamOfNodes()
                    .foldRight(List.apply(value), List::add);
        } else {
            return List.apply(parent);
        }
    }

    private CacheBuilder<Node> flattenNodeAttributes(Node node, CacheBuilder<Node> previous) throws StreamException, AttributeException {
        return node.apply(Attribute.Group.Node).foldRight(previous, (builder, type) -> {
            var child = node.apply(type).asNode();
            var childCache = CacheBuilder.apply(child);
            return builder.append(childCache, (current, next) -> current.with(type, new NodeAttribute(next)));
        });
    }

    private CacheBuilder<Node> flattenNodesAttributes(Node node, CacheBuilder<Node> previous) throws CompileException {
        try {
            return node.apply(Attribute.Group.Nodes).foldRight(previous, (builder, type) -> attachChildren(node, builder, type));
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
                .asStreamOfNodes()
                .map(CacheBuilder::apply)
                .foldRight(new CacheBuilder<>(List.createList()),
                        (previous, next) -> previous.append(next, List::add));
    }
}
