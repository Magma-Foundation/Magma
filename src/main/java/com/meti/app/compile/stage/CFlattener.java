package com.meti.app.compile.stage;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.cache.CacheBuilder;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute1;

public class CFlattener extends AbstractStage {
    @Override
    protected Node transformDefinition(Node definition) throws CompileException {
        return transformNodeAST(definition);
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
