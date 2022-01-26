package com.meti.app.compile.text;

import com.meti.api.core.F1;

public record StringOutput(String buffer) implements Output {
    public StringOutput() {
        this("");
    }

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
    public <E extends Exception> Output map(F1<String, String, E> mapper) throws E {
        return new StringOutput(mapper.apply(buffer));
    }

    @Override
    public Output prepend(String prefix) {
        return new StringOutput(prefix + buffer);
    }
}
