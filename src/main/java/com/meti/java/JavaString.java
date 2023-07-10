package com.meti.java;

import com.meti.collect.Collector;
import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;
import com.meti.iterate.Iterator;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class JavaString {
    private final String value;

    public JavaString(String value) {
        this.value = value;
    }

    public static JavaString empty() {
        return new JavaString("");
    }

    public static Collector<JavaString, Option<JavaString>> joining(final String delimiter) {
        return new Collector<>() {
            @Override
            public Option<JavaString> initial() {
                return new None<>();
            }

            @Override
            public Option<JavaString> foldLeft(Option<JavaString> accumulated, JavaString element) {
                return new Some<>(accumulated.map(value -> value
                        .append(delimiter)
                        .appendOwned(element)).unwrapOrElse(element));
            }
        };
    }

    public Option<Index> firstIndexOfChar(char c) {
        var index = this.value.indexOf(c);
        return index == -1
                ? new None<>()
                : Some.apply(createIndex(index));
    }

    public JavaString sliceFrom(Index index) {
        return new JavaString(value.substring(index.unwrap()));
    }

    public JavaString append(String value) {
        return new JavaString(this.value + value);
    }

    public String unwrap() {
        return value;
    }

    public boolean isEmpty() {
        return this.value.length() == 0;
    }

    public JavaString sliceTo(Index index) {
        return new JavaString(this.value.substring(0, index.value()));
    }

    public JavaString appendOwned(JavaString child) {
        return append(child.value);
    }

    public Option<Index> firstIndexOfSlice(String slice) {
        var index = this.value.indexOf(slice);
        if (index == -1) return new None<>();
        return Some.apply(createIndex(index));
    }

    private Index createIndex(int index) {
        return new Index(index, this.value.length());
    }

    public JavaString sliceBetween(Index start, Index end) {
        return new JavaString(this.value.substring(start.value(), end.value()));
    }

    public Option<Index> lastIndexOfChar(char c) {
        var index = this.value.lastIndexOf(c);
        return index == -1
                ? new None<>()
                : Some.apply(createIndex(index));
    }

    public Iterator<JavaString> split(String regex) {
        return new JavaList<>(Arrays.stream(this.value.split(regex))
                .map(JavaString::new)
                .collect(Collectors.toList()))
                .iter();
    }
}
