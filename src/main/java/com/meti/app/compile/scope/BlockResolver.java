package com.meti.app.compile.scope;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Primitive;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.stage.StreamStage;

public record BlockResolver(Node node, StreamStage<Node> parent) implements Processor<Node> {
    public Option<Node> process() throws CompileException {
        if (node.is(Node.Type.Block)) {
            try {
                var children = node.apply(Attribute.Type.Children)
                        .asStreamOfNodes()
                        .filter(value -> value.is(Node.Type.Return))
                        .foldRight(List.<Node>createList(), List::add);
                return new Some<>(children
                        .last()
                        .map(parent::transformNodeAST)
                        .orElse(Primitive.Void));
            } catch (StreamException e) {
                throw new CompileException(e);
            }
        } else {
            return new None<>();
        }
    }
}
