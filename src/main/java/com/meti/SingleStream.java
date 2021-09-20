package com.meti;

public class SingleStream implements Stream<Script> {
    private final Script value;

    public SingleStream(Script value) {
        this.value = value;
    }

    @Override
    public <E extends Exception> void forEach(C1E1<Script, E> consumer) throws E {
        consumer.apply(value);
    }
}
