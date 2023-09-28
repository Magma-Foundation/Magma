package com.meti.api.collect;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record JavaString(String value) {
    public static final JavaString Empty = new JavaString("");

    public Option<Index> firstIndexOfSlice(String slice) {
        return wrapIndex(value.indexOf(slice));
    }

    public Option<Index> firstIndexOfChar(int ch) {
        return wrapIndex(value.indexOf(ch));
    }

    private Option<Index> wrapIndex(int index) {
        return index == -1 ? None.apply() : Some.apply(new Index(index, value.length()));
    }

    public Option<Index> lastIndexOfChar(int ch) {
        return wrapIndex(value.lastIndexOf(ch));
    }

    public JavaString sliceFrom(Index beginIndex) {
        return new JavaString(value.substring(beginIndex.value()));
    }

    public JavaString sliceBetween(Range range) {
        return new JavaString(value.substring(range.startInclusive(), range.endExclusive()));
    }

    public JavaString sliceTo1(Index index) {
        return new JavaString(value.substring(0, index.value()));
    }

    public int length() {
        return value.length();
    }

    public boolean contains(String slice) {
        return this.value.contains(slice);
    }

    public JavaString strip() {
        return new JavaString(this.value.strip());
    }

    public JavaString concat(JavaString other) {
        return new JavaString(value + other.value);
    }

    public JavaString prepend(String slice) {
        return new JavaString(slice + value);
    }

    public JavaString append(String slice) {
        return new JavaString(value + slice);
    }
}
