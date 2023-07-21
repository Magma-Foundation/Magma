package com.meti.java;

import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Collector;
import com.meti.iterate.Index;
import com.meti.iterate.Iterator;
import com.meti.iterate.Range;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public final class JavaString implements String_ {
    public static String_ Empty = new JavaString("");
    private final java.lang.String value;

    private JavaString(java.lang.String value) {
        this.value = value;
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

    public static String_ fromSlice(String value) {
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
        return fromSlice(value.substring(index.unwrap()));
    }

    @Override
    public String_ append(String value) {
        return fromSlice(this.value + value);
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
        return fromSlice(this.value.substring(0, index.value()));
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
    public String_ sliceBetween(Range range) {
        return fromSlice(this.value.substring(range.start, range.to));
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
                .map(JavaString::fromSlice)
                .collect(Collectors.toList()))
                .iter();
    }

    @Override
    public String_ prepend(String prefix) {
        return new JavaString(prefix + this.value);
    }

    @Override
    public int compareTo(String_ other) {
        return this.value.compareTo(other.unwrap());
    }

    @Override
    public boolean equalsTo(String_ other) {
        return this.value.equals(other.unwrap());
    }

    @Override
    public String_ strip() {
        return new JavaString(this.value.strip());
    }

    @Override
    public boolean startsWith(String slice) {
        return this.value.startsWith(slice);
    }
}
