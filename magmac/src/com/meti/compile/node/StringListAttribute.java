package com.meti.compile.node;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.java.JavaString;

public class StringListAttribute implements Attribute {
    private final JavaList<JavaString> values;

    public StringListAttribute(JavaList<JavaString> values) {
        this.values = values;
    }

    @Override
    public Option<JavaList<JavaString>> asListOfStrings() {
        return Some.Some(values);
    }
}
