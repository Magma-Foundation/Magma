package com.meti.stream;

import java.util.List;

public class JavaListStream<T> implements Stream<T> {
    private final List<T> stream;
    private int counter = 0;

    public JavaListStream(List<T> stream) {
        this.stream = stream;
    }

    @Override
    public T head() throws StreamException {
        if (counter < stream.size()) {
            return stream.get(counter++);
        } else {
            throw new EndOfStreamException("No more elements left in this Java list.");
        }
    }
}
