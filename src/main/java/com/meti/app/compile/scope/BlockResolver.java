package com.meti.app.compile.scope;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.stage.StreamStage;
import com.meti.app.compile.stage.Transformer;

public record BlockResolver(Node node, StreamStage parent) implements Transformer {
    @Override
    public Option<Node> transform() throws CompileException {
        if (node.is(Node.Type.Block)) {
            try {
                var children = node.apply(Attribute.Type.Children)
                        .asStreamOfNodes1()
                        .filter(value -> value.is(Node.Type.Return))
                        .foldRight(List.<Node>createList(), List::add);
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
