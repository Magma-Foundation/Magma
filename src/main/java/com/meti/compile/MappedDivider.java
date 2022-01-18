package com.meti.compile;

import com.meti.api.collect.*;
import com.meti.compile.clang.CFormat;
import com.meti.compile.node.Node;
import com.meti.option.Option;

public abstract class MappedDivider implements Divider {
    protected final JavaMap<CFormat, JavaList<Node>> map;

    public MappedDivider(JavaMap<CFormat, JavaList<Node>> map) {
        this.map = map;
    }

    @Override
    public Stream<Node> apply(CFormat format) throws CollectionException {
        return map.applyOptionally(format)
                .map(JavaList::stream)
                .orElse(new EmptyStream<>());
    }

    @Override
    public Divider divide(Node node) throws CompileException {
        try {
            return decompose(node)
                    .map(JavaList::stream)
                    .orElse(Streams.apply(node))
                    .foldRight(this, MappedDivider::divideImpl);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract Option<JavaList<Node>> decompose(Node node) throws CompileException;

    private MappedDivider divideImpl(Node node) throws StreamException {
        var format = apply(node);
        var withKey = map.ensure(format, () -> generate(format));
        var withValue = withKey.mapValue(format, value -> modify(format, node, value));
        return complete(withValue);
    }

    protected abstract CFormat apply(Node node);

    protected abstract JavaList<Node> generate(CFormat format) throws StreamException;

    protected abstract JavaList<Node> modify(CFormat format, Node node, JavaList<Node> value);

    protected abstract MappedDivider complete(JavaMap<CFormat, JavaList<Node>> map) throws StreamException;
}
