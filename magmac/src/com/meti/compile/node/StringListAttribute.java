package com.meti.compile.node;

import com.meti.collect.JavaList;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Collectors;
import com.meti.java.JavaString;
import com.sun.source.util.JavacTask;

import java.util.Objects;

public class StringListAttribute implements Attribute {
    private final JavaList<JavaString> values;

    public StringListAttribute(JavaList<JavaString> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringListAttribute that = (StringListAttribute) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public Option<JavaList<JavaString>> asListOfStrings() {
        return Some.Some(values);
    }

    @Override
    public String toString() {
        var a = values.stream()
                .collect(Collectors.joining(JavaString.from(", ")))
                .orElse(JavaString.Empty);

        return "new StringListAttribute(JavaList.from(" + a.inner() + "))";
    }
}
