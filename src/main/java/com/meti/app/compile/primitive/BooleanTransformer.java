package com.meti.app.compile.primitive;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.stage.TransformationException;
import com.meti.app.compile.stage.Transformer;

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
