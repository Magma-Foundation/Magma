package com.meti.app.compile;

import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;

public class CMagmaNodeResolver extends StreamStage {
    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(() -> {
            if (node.is(Node.Type.Implementation)) {
                var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
                var type = oldIdentity.apply(Attribute.Type.Type).asNode();
                if (type.is(Node.Type.Implicit)) {
                    var value = node.apply(Attribute.Type.Value).asNode();
                    var newType = new MagmaTypeResolver().apply(value);
                    var newIdentity = oldIdentity.with(Attribute.Type.Type, new NodeAttribute(newType));
                    var newNode = node.with(Attribute.Type.Identity, new NodeAttribute(newIdentity));
                    return new Some<>(newNode);
                } else {
                    return new None<>();
                }
            }
            return new None<>();
        });
    }

    @Override
    protected Stream<Transformer> streamTypeTransformers(Node node) {
        return Streams.apply(new BooleanResolver(node));
    }

    private static class BooleanResolver implements Transformer {
        private final Node node;

        public BooleanResolver(Node node) {
            this.node = node;
        }

        @Override
        public Option<Node> transform() throws CompileException {
            if (node.is(Node.Type.Primitive) && node.apply(Attribute.Type.Name)
                    .asOutput()
                    .computeTrimmed()
                    .equals(Primitive.Bool.name())) {
                return new Some<>(new IntegerType(true, 16));
            } else {
                return new None<>();
            }
        }
    }
}
