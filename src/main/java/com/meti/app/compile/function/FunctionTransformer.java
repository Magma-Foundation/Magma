package com.meti.app.compile.function;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.feature.util.Cache;
import com.meti.app.compile.common.Definition;
import com.meti.app.compile.feature.scope.Variable;
import com.meti.app.compile.node.EmptyNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;

public record FunctionTransformer(Node oldNode) implements Processor<Node> {
    public Option<Node> process() throws CompileException {
        if (oldNode.is(Node.Category.Abstraction)) {
            return new Some<>(isExternal() ? EmptyNode.EmptyNode_ : oldNode);
        }
        if (oldNode.is(Node.Category.Implementation)) {
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
                    .asStreamOfFlags()
                    .foldRight(List.createList(), List::add)
                    .contains(Definition.Flag.Extern);
        } catch (StreamException | AttributeException e) {
            return false;
        }
    }

    private Node transformFunction() throws CompileException {
        try {
            var identity = oldNode.apply(Attribute.Type.Identity).asNode();
            Node function;
            if (identity.apply(Attribute.Type.Flags)
                        .asStreamOfFlags()
                        .count() == 0) {
                var name = identity.apply(Attribute.Type.Name).asInput();
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
