package com.meti.output;

import com.meti.option.Option;
import com.meti.option.Some;

public class EmptyOutput implements Output {
    @Override
    public Option<String> asString() {
        return new Some<>("");
    }
}
