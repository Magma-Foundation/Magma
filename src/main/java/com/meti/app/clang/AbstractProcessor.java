package com.meti.app.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

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
