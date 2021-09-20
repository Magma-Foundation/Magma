package com.meti;

public class SingleStream implements Source {
    private final Script script;

    public SingleStream(Script script) {
        this.script = script;
    }

    @Override
    public Stream stream() {
        return new Stream(script);
    }
}
