package com.meti;

public record StringOutput(String value) implements Output {
    @Override
    public Option<String> asString() {
        return new Some<>(value);
    }
}
