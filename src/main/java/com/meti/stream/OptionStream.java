package com.meti.stream;

import com.meti.option.Option;

public class OptionStream<T> implements Stream<T> {
    private final Option<T> value;
    private boolean retrieved = false;

    public OptionStream(Option<T> value) {
        this.value = value;
    }

    @Override
    public T head() throws StreamException {
        if (retrieved) {
            throw new EndOfStreamException("Value has already been retrieved.");
        } else {
            retrieved = true;
            return value.orElseThrow(() -> new EndOfStreamException("No value present in option in stream."));
        }
    }
}
