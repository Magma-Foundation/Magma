package com.meti;

public abstract class AbstractProcessor<T> implements Processor<T> {
    @Override
    public Option<T> process() throws CompileException {
        return isValid() ?
                new Some<>(processValid()) :
                new None<>();
    }

    protected abstract boolean isValid();

    protected abstract T processValid() throws CompileException;
}
