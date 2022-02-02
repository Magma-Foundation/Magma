package com.meti.app.compile.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractTypeRenderer<T> implements Processor<Node> {
    protected final Node identity;
    protected final Node.Role role;

    public AbstractTypeRenderer(Node identity, Node.Role role) {
        this.identity = identity;
        this.role = role;
    }

    @Override
    public Option<Node> process() throws CompileException {
        return identity.apply(Attribute.Type.Type).asNode().is(role)
                ? new Some<>(toNode(processImpl()))
                : new None<>();
    }

    protected abstract Node toNode(T node) throws CompileException;

    protected abstract T processImpl() throws CompileException;
}
