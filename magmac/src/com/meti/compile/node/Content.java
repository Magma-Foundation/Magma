package com.meti.compile.node;

import com.meti.collect.option.Option;
import com.meti.java.JavaString;

import java.util.Objects;

import static com.meti.collect.option.Some.Some;

public class Content implements Node {
    private final String value;
    private final int indent;

    public Content(JavaString value, int indent) {
        this.value = value.inner();
        this.indent = indent;
    }

    public Content(String value, int indent) {
        this.value = value;
        this.indent = indent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return indent == content.indent && Objects.equals(value, content.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, indent);
    }

    @Override
    public Option<String> findValueAsString() {
        return Some(value);
    }

    @Override
    public Option<Integer> findIndent() {
        return Some(indent);
    }

    @Override
    public String toString() {
        return "Content{" +
               "value='" + value + '\'' +
               ", indent=" + indent +
               '}';
    }

    @Override
    public Option<String> render() {
        return Some(value);
    }

    @Override
    public boolean is(String name) {
        throw new UnsupportedOperationException();
    }
}
