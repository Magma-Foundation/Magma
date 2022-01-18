package com.meti.app.compile;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.node.Node;

public class CMagmaModifier extends StreamStage {
    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(
                new BooleanTransformer(node),
                new BlockTransformer(node),
                new FunctionTransformer(node));
    }
}
