package com.meti.app.compile.node.output;

import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record StringOutput(String value) implements Output {
    @Override
    public Option<String> asString() {
        return new Some<>(value);
    }
}
