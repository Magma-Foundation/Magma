package com.meti.compile;

import com.meti.collect.Stream;
import com.meti.collect.Streams;
import com.meti.compile.node.Node;

public class CMagmaModifier extends AbstractTransformer {
    @Override
    protected Stream<Transformer> stream(Node node) {
        return Streams.apply(
                new BooleanTransformer(node),
                new BlockTransformer(node),
                new FunctionTransformer(node));
    }
}
