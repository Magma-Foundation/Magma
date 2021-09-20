package com.meti;

public class Source {
    private final Script script;

    public Source(Script script) {
        this.script = script;
    }

    public Stream stream() {
        return new Stream(script);
    }
}
