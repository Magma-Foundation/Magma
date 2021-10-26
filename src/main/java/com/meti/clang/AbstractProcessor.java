package com.meti.clang;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public abstract class AbstractProcessor<T> implements Processor<T> {
    @Override
    public Option<T> process() {
        return validate() ?
                new Some<>(processDefined()) :
                new None<>();
    }

    protected abstract boolean validate();

    protected abstract T processDefined();
}
