package com.meti.compile;

import com.meti.api.collect.JavaList;
import com.meti.api.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.cache.Cache;
import com.meti.compile.common.Field;
import com.meti.compile.common.variable.Variable;
import com.meti.compile.node.EmptyNode;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionTransformer(Node oldNode) implements Transformer {
    @Override
    public Option<Node> transform() throws CompileException {
        if (oldNode.is(Node.Type.Abstraction)) {
            return new Some<>(isExternal() ? EmptyNode.EmptyNode_ : oldNode);
        }
        if (oldNode.is(Node.Type.Implementation)) {
            var function = transformFunction();
            return new Some<>(function);
        }
        return new None<>();
    }

    private boolean isExternal() {
        try {
            return oldNode.apply(Attribute.Type.Identity)
                    .asNode()
                    .apply(Attribute.Type.Flags)
                    .asStreamOfFlags1()
                    .foldRight(new JavaList<>(), JavaList::add)
                    .contains(Field.Flag.Extern);
        } catch (StreamException | AttributeException e) {
            return false;
        }
    }

    private Node transformFunction() throws CompileException {
        try {
            var identity = oldNode.apply(Attribute.Type.Identity).asNode();
            Node function;
            if (identity.apply(Attribute.Type.Flags)
                        .asStreamOfFlags1()
                        .count() == 0) {
                var name = identity.apply(Attribute.Type.Name).asText();
                function = new Cache(new Variable(name), oldNode);
            } else {
                function = oldNode;
            }
            return function;
        } catch (StreamException | AttributeException e) {
            throw new CompileException(e);
        }
    }
}
