package com.meti.node;

import com.meti.api.option.Option;
import com.meti.compile.JavaString;

import java.util.Optional;

public interface Attribute {
    default Optional<Integer> asInt() {
        return Optional.empty();
    }

    Option<JavaString> asString();
}
