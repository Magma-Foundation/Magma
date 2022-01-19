package com.meti.app.compile.stage;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.function.FunctionTransformer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.BooleanTransformer;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.scope.BlockTransformer;

public class CMagmaModifier extends StreamStage {
    @Override
    protected Stream<Processor<Node>> streamNodeTransformers(Node node) {
        return Streams.apply(
                new BooleanTransformer(node),
                new BlockTransformer(node),
                new FunctionTransformer(node));
    }
}
