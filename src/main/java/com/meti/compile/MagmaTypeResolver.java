package com.meti.compile;

import com.meti.collect.Stream;
import com.meti.collect.Streams;
import com.meti.compile.node.Node;

public class MagmaTypeResolver extends StreamStage {
    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(
                new BlockResolver(node, this),
                new ReturnResolver(node, this),
                new IntResolver(node));
    }
}
