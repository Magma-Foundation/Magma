package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.common.Field;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionTransformer(Node node) implements Transformer {
    @Override
    public Option<Node> transform() {
        if (node.is(Node.Type.Abstraction)) {
            return new Some<>(isExternal() ? new EmptyNode() : node);
        }
        return new None<>();
    }

    private boolean isExternal() {
        try {
            return node.apply(Attribute.Type.Identity)
                    .asNode()
                    .apply(Attribute.Type.Flags)
                    .asStreamOfFlags1()
                    .foldRight(new JavaList<>(), JavaList::add)
                    .contains(Field.Flag.Extern);
        } catch (StreamException | AttributeException e) {
            return false;
        }
    }
}
