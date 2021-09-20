package com.meti;

import java.io.IOException;

public class Stream {
    private final Script value;

    public Stream(Script value) {
        this.value = value;
    }

    public Script getValue() {
        return value;
    }

    void forEach(C1E1<Script, IOException> consumer) throws IOException {
        consumer.apply(getValue());
    }
}
