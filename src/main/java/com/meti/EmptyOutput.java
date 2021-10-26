package com.meti;

public class EmptyOutput implements Output {
    @Override
    public Option<String> asString() {
        return new Some<>("");
    }
}
