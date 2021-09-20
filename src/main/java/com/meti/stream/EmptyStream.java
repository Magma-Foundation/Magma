package com.meti.stream;

import com.meti.EndOfStreamException;

public class EmptyStream<T> extends AbstractStream<T> {
    @Override
    protected T head() throws StreamException {
        throw new EndOfStreamException();
    }
}
