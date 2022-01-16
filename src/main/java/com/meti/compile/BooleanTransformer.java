package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record BooleanTransformer(Node node) implements Transformer {
    @Override
    public Option<Node> transform() throws TransformationException {
        if (node.is(Node.Type.Boolean)) {
            try {
                var value = node.apply(Attribute.Type.Value);
                var state = value.asBoolean();
                return new Some<>(new IntegerNode(state ? 1 : 0));
            } catch (AttributeException e) {
                throw new TransformationException(e);
            }
        }
        return new None<>();
    }
}
