package com.meti.compile.attribute;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record StringAttribute(JavaString value) implements Attribute {
    @Override
    public Option<JavaString> asString() {
        return Some.apply(value);
    }

    @Override
    public JavaString toXML() {
        return value.prepend("\"").append("\"");
    }
}
