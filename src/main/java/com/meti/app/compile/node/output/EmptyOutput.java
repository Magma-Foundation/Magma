package com.meti.app.compile.node.output;

import com.meti.api.option.Option;
import com.meti.api.option.Some;

public class EmptyOutput implements Output {
    @Override
    public Option<String> asString() {
        return new Some<>("");
    }
}
