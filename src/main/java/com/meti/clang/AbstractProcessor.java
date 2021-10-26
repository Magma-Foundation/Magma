package com.meti.clang;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public abstract class AbstractProcessor<T> implements Processor<T> {
    @Override
    public Option<T> process() {
        return isValid() ?
                new Some<>(processValid()) :
                new None<>();
    }

    protected abstract boolean isValid();

    protected abstract T processValid();
}
