package com.meti;

import java.util.List;

public class JavaListStream<T> implements Stream<T> {
    private final List<T> list;

    public JavaListStream(List<T> list) {
        this.list = list;
    }

    @Override
    public <E extends Exception> void forEach(C1E1<T, E> consumer) throws E {
        for (T element : list) {
            consumer.apply(element);
        }
    }
}
