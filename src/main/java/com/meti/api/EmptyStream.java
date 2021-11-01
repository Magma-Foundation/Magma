package com.meti.api;

public class EmptyStream<T> implements AbstractStream<T> {
    @Override
    public T head() throws StreamException {
        throw new EndOfStreamException("Stream is always empty.");
    }
}
