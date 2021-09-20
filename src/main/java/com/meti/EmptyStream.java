package com.meti;

public class EmptyStream<T> extends AbstractStream<T> {
    @Override
    protected T head() throws StreamException {
        throw new EndOfStreamException();
    }
}
