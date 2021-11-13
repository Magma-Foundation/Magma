package com.meti.process;

import com.meti.CompileException;
import com.meti.Processor;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
