package com.meti.api;

import java.util.List;

public class ListStream<T> implements AbstractStream<T> {
    private final List<T> list;
    private int counter = 0;

    public ListStream(List<T> list) {
        this.list = list;
    }

    @Override
    public T head() throws StreamException {
        if (counter < list.size()) {
            return list.get(counter++);
        }
        throw new EndOfStreamException("No more elements left.");
    }
}
