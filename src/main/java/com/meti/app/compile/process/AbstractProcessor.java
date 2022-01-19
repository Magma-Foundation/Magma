package com.meti.app.compile.process;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractProcessor<T> implements Processor<T> {
    protected final Node node;

    public AbstractProcessor(Node node) {
        this.node = node;
    }

    @Override
    public Option<T> process() throws CompileException {
        return validate() ? new Some<>(createNode()) : new None<T>();
    }

    protected abstract boolean validate();

    protected abstract T createNode() throws CompileException;
}
