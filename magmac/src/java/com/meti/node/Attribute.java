package com.meti.node;

import com.meti.collect.JavaString;
import com.meti.option.None;
import com.meti.option.Option;

import java.util.List;

public interface Attribute {
    default Option<JavaString> asString() {
        return new None<>();
    }

    default Option<List<JavaString>> asListOfStrings() {
        return new None<>();
    }
}
