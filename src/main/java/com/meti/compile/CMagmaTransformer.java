package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.common.integer.IntegerNode;
import com.meti.compile.node.Node;

public record CMagmaTransformer() {
    Node transform(Node node) throws TransformationException {
        if (node.is(Node.Type.Boolean)) {
            try {
                var value = node.apply(Attribute.Type.Value);
                var state = value.asBoolean();
                return new IntegerNode(state ? 1 : 0);
            } catch (AttributeException e) {
                throw new TransformationException(e);
            }
        }
        return node;
    }
}
