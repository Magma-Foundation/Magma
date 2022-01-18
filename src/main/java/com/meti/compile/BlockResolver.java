package com.meti.compile;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Primitive;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

record BlockResolver(Node node, StreamStage parent) implements Transformer {
    @Override
    public Option<Node> transform() throws CompileException {
        if (node.is(Node.Type.Block)) {
            try {
                var children = node.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .filter(value -> value.is(Node.Type.Return))
                        .foldRight(new JavaList<Node>(), JavaList::add);
                return new Some<>(children
                        .last()
                        .map(parent::apply)
                        .orElse(Primitive.Void));
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        } else {
            return new None<>();
        }
    }
}
