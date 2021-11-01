package com.meti.api;

import com.meti.api.option.Option;

public class OptionStream<T> implements AbstractStream<T> {
    private final Option<T> option;
    private boolean retrieved = false;

    public OptionStream(Option<T> option) {
        this.option = option;
    }

    @Override
    public T head() throws EndOfStreamException {
        if (!retrieved) {
            retrieved = true;
            return option.orElseThrow(this::createEmptyException);
        } else {
            throw createEmptyException();
        }
    }

    private EndOfStreamException createEmptyException() {
        return new EndOfStreamException("Stream is empty.");
    }
}
