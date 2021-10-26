package com.meti;

public record StringOutput(String value) implements Output {
    @Override
    public String compute() {
        return value;
    }
}
