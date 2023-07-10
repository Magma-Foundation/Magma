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

    public static Collector<JavaString, Option<JavaString>> joining(JavaString delimiter) {
        return new Collector<>() {
            @Override
            public Option<JavaString> initial() {
                return new None<>();
            }

            @Override
            public Option<JavaString> foldLeft(Option<JavaString> accumulated, JavaString element) {
                return new Some<>(accumulated
                        .map(value -> value.concat(delimiter.value).concat(element.value))
                        .unwrapOrElse(element));
            }
        };
    }

    public Option<Index> firstIndexOfChar(char c) {
        var index = this.value.indexOf(c);
        return index == -1
                ? new None<>()
                : Some.apply(new Index(index, this.value.length()));
    }

    public JavaString sliceToEnd(Index index) {
        return new JavaString(value.substring(0, index.unwrap()));
    }

    public JavaString concat(String value) {
        return new JavaString(this.value + value);
    }

    public String unwrap() {
        return value;
    }

    public Iterator<JavaString> split(String regex) {
        return new JavaList<>(Arrays.stream(value.split(regex))
                .map(JavaString::new)
                .collect(Collectors.toList()))
                .iter();
    }

    public boolean startsWith(String slice) {
        if (slice.length() > this.value.length()) return false;

        for (int i = 0; i < slice.length(); i++) {
            if (this.value.charAt(i) != slice.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public Option<Index> firstIndexOfSlice(String value) {
        var index = this.value.indexOf(value);
        if (index == -1) return new None<>();
        else return new Some<>(new Index(index, this.value.length()));
    }

    public JavaString sliceFrom(Index extent) {
        return new JavaString(this.value.substring(extent.value()));
    }

    public JavaString prepend(String slice) {
        return new JavaString(slice + this.value);
    }

    public JavaString prependOwned(JavaString slice) {
        return prepend(slice.value);
    }
}
