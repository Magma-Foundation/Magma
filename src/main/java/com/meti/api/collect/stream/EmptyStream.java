package com.meti.api.collect.stream;

public class EmptyStream<T> extends AbstractStream<T> {
    @Override
    public T head() throws StreamException {
        throw new EndOfStreamException("Stream is empty.");
    }
}
