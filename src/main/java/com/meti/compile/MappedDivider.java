package com.meti.compile;

import com.meti.collect.*;
import com.meti.compile.clang.CFormat;
import com.meti.compile.node.Node;
import com.meti.option.Option;

public abstract class MappedDivider implements Divider<MappedDivider> {
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
    public MappedDivider divide(Node node) throws CompileException {
        return decompose(node).orElseGet(() -> getMappedDivider(node, this));
    }

    protected abstract Option<MappedDivider> decompose(Node node) throws CompileException;

    private MappedDivider getMappedDivider(Node node, MappedDivider divider) throws CompileException {
        try {
            var format = divider.apply(node);
            var withKey = divider.map.ensure(format, () -> divider.generate(format));
            var withValue = withKey.mapValue(format, value -> divider.modify(format, node, value));
            return divider.complete(withValue);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    protected abstract CFormat apply(Node node);

    protected abstract JavaList<Node> generate(CFormat format) throws StreamException;

    protected abstract JavaList<Node> modify(CFormat format, Node node, JavaList<Node> value);

    protected abstract MappedDivider complete(JavaMap<CFormat, JavaList<Node>> map) throws StreamException;
}
