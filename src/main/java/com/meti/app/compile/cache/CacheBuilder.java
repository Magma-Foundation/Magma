package com.meti.app.compile.cache;

import com.meti.api.collect.java.List;
import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.core.F1;
import com.meti.api.core.F2;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.node.Node;

public record CacheBuilder<T>(T value, List<Node> items) {
    public CacheBuilder(T root, Node... items) {
        this(root, List.apply(items));
    }

    public static CacheBuilder<Node> apply(Node root) {
        if (root.is(Node.Type.Cache)) {
            try {
                var value = root.apply(Attribute.Type.Value).asNode();
                var children = root.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .foldRight(List.<Node>createList(), List::add);
                return new CacheBuilder<>(value, children);
            } catch (AttributeException | StreamException e) {
                return new CacheBuilder<>(root, List.createList());
            }
        } else {
            return new CacheBuilder<>(root, List.createList());
        }
    }

    public CacheBuilder<T> append(List<Node> children) {
        return new CacheBuilder<>(value, items.addAll(children));
    }

    public <R, E extends Exception> CacheBuilder<T> append(CacheBuilder<R> other, F2<T, R, T, E> strategy) throws AttributeException, E {
        var newNode = strategy.apply(value, other.value);
        var newItems = items.addAll(other.items);
        return new CacheBuilder<>(newNode, newItems);
    }

    public <E extends Exception> Node build(F1<T, Node, E> strategy) throws E {
        var node = strategy.apply(value);
        return items.isEmpty() ? node : new Cache(node, items);
    }

    public <R, E extends Exception> CacheBuilder<R> map(F1<T, R, E> mapper) throws E {
        return new CacheBuilder<>(mapper.apply(value), items);
    }
}
