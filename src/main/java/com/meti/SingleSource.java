package com.meti;

public class SingleSource implements Source {
    private final Script script;

    public SingleSource(Script script) {
        this.script = script;
    }

    @Override
    public Stream<Script> stream() {
        if (script.exists()) {
            return new SingleStream(script);
        } else {
            return new EmptyStream<>();
        }
    }
}
