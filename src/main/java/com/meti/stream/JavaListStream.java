package com.meti.stream;

import com.meti.EndOfStreamException;

import java.util.List;

public class JavaListStream<T> extends AbstractStream<T> {
    private final List<T> list;
    private int counter = 0;

    public JavaListStream(List<T> list) {
        this.list = list;
    }

    @Override
    protected T head() throws EndOfStreamException {
        if (counter < list.size()) {
            return list.get(counter++);
        } else {
            throw new EndOfStreamException();
        }
    }
}
