package com.meti.compile.cache;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.node.Node;
import com.meti.core.F1;
import com.meti.core.F2;

public record CacheBuilder<T>(T value, JavaList<Node> items) {
    public CacheBuilder(T root, Node... items) {
        this(root, JavaList.apply(items));
    }

    public static CacheBuilder<Node> apply(Node root) {
        if (root.is(Node.Type.Cache)) {
            try {
                var value = root.apply(Attribute.Type.Value).asNode();
                var children = root.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .foldRight(new JavaList<Node>(), JavaList::add);
                return new CacheBuilder<>(value, children);
            } catch (AttributeException | StreamException e) {
                return new CacheBuilder<>(root, new JavaList<>());
            }
        } else {
            return new CacheBuilder<>(root, new JavaList<>());
        }
    }

    public CacheBuilder<T> append(JavaList<Node> children) {
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
