package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.JavaMap;
import com.meti.collect.Stream;
import com.meti.collect.StreamException;
import com.meti.compile.clang.CFormat;
import com.meti.compile.node.Node;

public abstract class MappedDivision implements Division {
    protected final JavaMap<CFormat, JavaList<Node>> map;

    public MappedDivision(JavaMap<CFormat, JavaList<Node>> map) {
        this.map = map;
    }

    @Override
    public Stream<Node> apply(CFormat format) {
        return map.apply(format).stream();
    }

    @Override
    public Division divide(Node node) throws StreamException {
        var format = apply(node);
        var withKey = map.ensure(format, () -> generate(format, node));
        var withValue = withKey.mapValue(format, value -> modify(format, node, value));
        return complete(withValue);
    }

    protected abstract CFormat apply(Node node);

    public abstract JavaList<Node> generate(CFormat format, Node node) throws StreamException;

    protected abstract JavaList<Node> modify(CFormat format, Node node, JavaList<Node> value);

    protected abstract Division complete(JavaMap<CFormat, JavaList<Node>> map) throws StreamException;
}
