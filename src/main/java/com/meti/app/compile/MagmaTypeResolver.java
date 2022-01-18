package com.meti.app.compile;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;

public class MagmaTypeResolver extends StreamStage {
    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(
                new BlockResolver(node, this),
                new ReturnResolver(node, this),
                new IntResolver(node));
    }
}
