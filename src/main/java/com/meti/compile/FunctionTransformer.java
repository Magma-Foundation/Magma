package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.common.Field;
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
            var function = transformImplementation();
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

    private Node transformImplementation() throws CompileException {
        try {
            var outerValue = this.oldNode.apply(Attribute.Type.Value).asNode();
            if (outerValue.is(Node.Type.Cache)) {
                return transformCachedImplementation(outerValue);
            } else {
                return oldNode;
            }
        } catch (AttributeException | StreamException e) {
            throw new CompileException(e);
        }
    }

    private Node transformCachedImplementation(Node outerValue) throws AttributeException, StreamException {
        var innerValue = outerValue.apply(Attribute.Type.Value).asNode();
        var newNode = oldNode.with(Attribute.Type.Value, new NodeAttribute(innerValue));

        return outerValue.apply(Attribute.Type.Children)
                .asStreamOfNodes1()
                .foldRight(new Cache.Builder(newNode), Cache.Builder::add)
                .build();
    }
}
