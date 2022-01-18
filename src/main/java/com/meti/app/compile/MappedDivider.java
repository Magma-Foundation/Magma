package com.meti.app.compile;

import com.meti.api.collect.CollectionException;
import com.meti.api.collect.java.List;
import com.meti.api.collect.java.JavaMap;
import com.meti.api.collect.stream.EmptyStream;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.Option;
import com.meti.app.compile.clang.CFormat;
import com.meti.app.compile.node.Node;

public abstract class MappedDivider implements Divider {
    protected final JavaMap<CFormat, List<Node>> map;

    public MappedDivider(JavaMap<CFormat, List<Node>> map) {
        this.map = map;
    }

    @Override
    public Stream<Node> apply(CFormat format) throws CollectionException {
        return map.applyOptionally(format)
                .map(List::stream)
                .orElse(new EmptyStream<>());
    }

    @Override
    public Divider divide(Node node) throws CompileException {
        try {
            return decompose(node)
                    .map(List::stream)
                    .orElse(Streams.apply(node))
                    .foldRight(this, MappedDivider::divideImpl);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Option<List<Node>> decompose(Node node) throws CompileException;

    private MappedDivider divideImpl(Node node) throws StreamException {
        var format = apply(node);
        var withKey = map.ensure(format, () -> generate(format));
        var withValue = withKey.mapValue(format, value -> modify(format, node, value));
        return complete(withValue);
    }

    protected abstract CFormat apply(Node node);

    protected abstract List<Node> generate(CFormat format) throws StreamException;

    protected abstract List<Node> modify(CFormat format, Node node, List<Node> value);

    protected abstract MappedDivider complete(JavaMap<CFormat, List<Node>> map) throws StreamException;
}
