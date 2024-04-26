package com.meti.node;

import com.meti.collect.JavaString;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.List;

public class StringListAttribute implements Attribute {
    private final List<JavaString> values;

    public StringListAttribute(List<JavaString> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return values.toString();
    }

    @Override
    public Option<List<JavaString>> asListOfStrings() {
        return new Some<>(values);
    }
}
