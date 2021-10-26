package com.meti.compile.output;

import com.meti.option.Option;
import com.meti.option.Some;

public record StringOutput(String value) implements Output {
    @Override
    public Option<String> asString() {
        return new Some<>(value);
    }
}
