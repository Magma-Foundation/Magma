package com.meti.java;

import com.meti.collect.Collector;
import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Iterator;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public final class JavaString implements String_ {
    private final java.lang.String value;

    private JavaString(java.lang.String value) {
        this.value = value;
    }

    public static String_ empty() {
        return from("");
    }

    public static Collector<String_, Option<String_>> joining(final String_ delimiter) {
        return new Collector<>() {
            @Override
            public Option<String_> initial() {
                return new None<>();
            }

            @Override
            public Option<String_> foldLeft(Option<String_> accumulated, String_ element) {
                return new Some<>(accumulated.map(value -> value
                        .appendOwned(delimiter)
                        .appendOwned(element)).unwrapOrElse(element));
            }
        };
    }

    public static String_ from(String value) {
        return new JavaString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JavaString that = (JavaString) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public Option<Index> firstIndexOfChar(char c) {
        var index = this.value.indexOf(c);
        return index == -1
                ? new None<>()
                : Some.apply(createIndex(index));
    }

    @Override
    public String_ sliceFrom(Index index) {
        return from(value.substring(index.unwrap()));
    }

    @Override
    public String_ append(String value) {
        return from(this.value + value);
    }

    @Override
    public java.lang.String unwrap() {
        return value;
    }

    @Override
    public boolean isEmpty() {
        return this.value.length() == 0;
    }

    @Override
    public String_ sliceTo(Index index) {
        return from(this.value.substring(0, index.value()));
    }

    @Override
    public String_ appendOwned(String_ child) {
        return append(child.unwrap());
    }

    @Override
    public Option<Index> firstIndexOfSlice(java.lang.String slice) {
        var index = this.value.indexOf(slice);
        if (index == -1) return new None<>();
        return Some.apply(createIndex(index));
    }

    private Index createIndex(int index) {
        return new Index(index, this.value.length());
    }

    @Override
    public String_ sliceBetween(Index start, Index end) {
        return from(this.value.substring(start.value(), end.value()));
    }

    @Override
    public Option<Index> lastIndexOfChar(char c) {
        var index = this.value.lastIndexOf(c);
        return index == -1
                ? new None<>()
                : Some.apply(createIndex(index));
    }

    @Override
    public Iterator<String_> split(String regex) {
        return new JavaList<>(Arrays.stream(this.value.split(regex))
                .map(JavaString::from)
                .collect(Collectors.toList()))
                .iter();
    }
}
