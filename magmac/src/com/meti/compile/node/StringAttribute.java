package com.meti.compile.node;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.java.JavaString;

public record StringAttribute(JavaString value) implements Attribute {
    @Override
    public Option<JavaString> asString() {
        return Some.Some(value);
    }

    @Override
    public String toString() {
        return "new StringAttribute(" + value.toString() + ")";
    }
}
