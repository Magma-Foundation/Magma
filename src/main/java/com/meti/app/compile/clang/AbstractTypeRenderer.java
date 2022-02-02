package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractTypeRenderer implements Processor<Node> {
    protected final Node identity;
    private final Node.Type type;

    protected AbstractTypeRenderer(Node identity, Node.Type type) {
        this.identity = identity;
        this.type = type;
    }

    @Override
    public Option<Node> process() throws CompileException {
        return identity.apply(Attribute.Type.Type).asNode().is(type)
                ? new Some<>(processValid())
                : new None<>();
    }

    protected abstract Node processValid() throws CompileException;
}
