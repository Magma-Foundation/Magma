package com.meti.app.process;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.CompileException;

public abstract class FilteredProcessor<T> implements Processor<T> {
    @Override
    public Option<T> process() throws CompileException {
        return isValid() ?
                new Some<>(processValid()) :
                new None<>();
    }

    protected abstract boolean isValid();

    protected abstract T processValid() throws CompileException;
}
