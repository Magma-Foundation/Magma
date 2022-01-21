package com.meti.app.compile.text;

public record StringOutput(String buffer) implements Output {

    @Override
    public Output appendOutput(Output other) {
        return new StringOutput(buffer + other.compute());
    }

    @Override
    public Output appendSlice(String slice) {
        return new StringOutput(buffer + slice);
    }

    @Override
    public String compute() {
        return buffer.trim();
    }

    @Override
    public String computeRaw() {
        return buffer;
    }

    @Override
    public Output prepend(String prefix) {
        return new StringOutput(prefix + buffer);
    }
}
