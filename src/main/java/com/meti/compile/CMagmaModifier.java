package com.meti.compile;

import com.meti.api.collect.Stream;
import com.meti.api.collect.Streams;
import com.meti.compile.node.Node;

public class CMagmaModifier extends StreamStage {
    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(
                new BooleanTransformer(node),
                new BlockTransformer(node),
                new FunctionTransformer(node));
    }
}
