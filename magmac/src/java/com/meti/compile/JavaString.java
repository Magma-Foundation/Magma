package com.meti.compile;

import com.meti.api.Index;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.api.result.Tuple;

public record JavaString(String value) {
    private static Option<Index> wrapIndex(int index) {
        return index == -1
                ? new None<>()
                : new Some<>(new Index(index));
    }

    Option<Index> firstIndexOfChar(char c) {
        return wrapIndex(this.value.indexOf(c));
    }

    JavaString strip() {
        return new JavaString(value.strip());
    }

    public String value() {
        return value;
    }

    public Option<Index> lastIndexOfChar(char c) {
        return wrapIndex(this.value.lastIndexOf(c));
    }

    public Option<Tuple<JavaString, JavaString>> splitAtLastChar(char c) {
        return lastIndexOfChar(c).flatMap(index -> {
            var substring = sliceTo(index);
            return index.next().map(inner -> new Tuple<>(substring, sliceFrom(inner)));
        });
    }

    private JavaString sliceFrom(Index beginIndex) {
        return new JavaString(value.substring(beginIndex.value()));
    }

    private JavaString sliceTo(Index index) {
        return new JavaString(value.substring(0, index.value()));
    }
}