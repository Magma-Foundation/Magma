package com.meti;

import java.io.IOException;

public class SingleStream implements Stream {
    private final Script value;

    public SingleStream(Script value) {
        this.value = value;
    }

    @Override
    public void forEach(C1E1<Script, IOException> consumer) throws IOException {
        consumer.apply(value);
    }
}
