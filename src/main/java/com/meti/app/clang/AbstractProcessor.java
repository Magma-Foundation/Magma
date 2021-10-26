package com.meti.app.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.attribute.CompileException;

public abstract class AbstractProcessor<T> implements Processor<T> {
    @Override
    public Option<T> process() throws CompileException {
        return validate() ?
                new Some<>(processDefined()) :
                new None<>();
    }

    protected abstract boolean validate();

    protected abstract T processDefined() throws CompileException;
}
