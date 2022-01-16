package com.meti.compile;

import com.meti.collect.Stream;
import com.meti.collect.Streams;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.common.integer.IntegerType;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.option.None;
import com.meti.option.Some;

public class CMagmaNodeResolver extends AbstractTransformationStage {
    @Override
    protected Stream<Transformer> streamNodeTransformers(Node node) {
        return Streams.apply(() -> {
            if (node.is(Node.Type.Implementation)) {
                var oldIdentity = node.apply(Attribute.Type.Identity).asNode();
                var type = oldIdentity.apply(Attribute.Type.Type).asNode();
                if (type.is(Node.Type.Implicit)) {
                    var value = node.apply(Attribute.Type.Value).asNode();
                    var newType = new MagmaTypeResolver().transformNode(value);
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
        return Streams.apply(() -> {
            if (node.is(Node.Type.Primitive) && node.apply(Attribute.Type.Name)
                    .asText()
                    .computeTrimmed()
                    .equals(Primitive.Bool.name())) {
                return new Some<>(new IntegerType(true, 16));
            } else {
                return new None<>();
            }
        });
    }
}
