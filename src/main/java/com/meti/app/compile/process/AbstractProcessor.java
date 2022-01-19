package com.meti.app.compile.process;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.stage.CompileException;

public abstract class AbstractProcessor<I, O> implements Processor<O> {
    protected final I input;

    public AbstractProcessor(I input) {
        this.input = input;
    }

    @Override
    public Option<O> process() throws CompileException {
        return validate() ? new Some<>(createNode()) : new None<O>();
    }

    protected abstract boolean validate();

    protected abstract O createNode() throws CompileException;
}
