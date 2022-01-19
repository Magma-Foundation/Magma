package com.meti.app.compile.magma;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.function.ReturnResolver;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.primitive.IntResolver;
import com.meti.app.compile.scope.BlockResolver;
import com.meti.app.compile.stage.StreamStage;
import com.meti.app.compile.stage.Transformer;

public class MagmaTypeResolver extends StreamStage {
    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(
                new BlockResolver(node, this),
                new ReturnResolver(node, this),
                new IntResolver(node));
    }
}
