package com.meti;

public class SingleSource implements Source {
    private final Script script;

    public SingleSource(Script script) {
        this.script = script;
    }

    @Override
    public Stream<Script> stream() {
        return new SingleStream(script);
    }
}
