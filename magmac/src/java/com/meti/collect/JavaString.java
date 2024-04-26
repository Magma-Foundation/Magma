package com.meti.collect;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public record JavaString(String value) {
    public static final JavaString EMPTY = new JavaString("");

    @Override
    public String toString() {
        return value.toString();
    }

    public Option<Index> lastIndexOfChar(char c) {
        var index = value.lastIndexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(createIndex(index));
    }

    private Index createIndex(int index) {
        return new Index(index, value.length());
    }

    public Option<Range> firstRangeOfSlice(String slice) {
        var index = value.indexOf(slice);
        return index == -1
                ? new None<>()
                : new Some<>(new Range(index, index + slice.length(), value.length()));
    }

    public JavaString concat(JavaString other) {
        return new JavaString(value + other.value);
    }

    public Option<Index> firstIndexOfChar(char blockStart) {
        var index = value.indexOf(blockStart);
        return index == -1
                ? new None<>()
                : new Some<>(createIndex(index));
    }

    public JavaString sliceBetween(Index start, Index end) {
        return new JavaString(value.substring(start.value(), end.value()));
    }

    public JavaString strip() {
        return new JavaString(this.value.strip());
    }

    public boolean startsWithSlice(String slice) {
        return this.value.startsWith(slice);
    }

    public JavaString sliceFrom(Index start) {
        return new JavaString(this.value.substring(start.value()));
    }

    public JavaString sliceTo(Index end) {
        return new JavaString(this.value.substring(0, end.value()));
    }

    public Option<Tuple<JavaString, JavaString>> splitAtFirstIndexOfCharExclusive(char c) {
        return firstIndexOfChar(c).map(index -> new Tuple<>(sliceTo(index), sliceFrom(index.next().orElse(end()))));
    }

    public Index end() {
        return createIndex(this.value.length());
    }

    public List<JavaString> splitBySlice(String sliceRegex) {
        return Arrays.stream(this.value.split(sliceRegex))
                .map(JavaString::new)
                .collect(Collectors.toList());
    }

    public boolean equalsToSlice(String slice) {
        return this.value.equals(slice);
    }

    public Option<Tuple<JavaString, JavaString>> splitAtLastIndexOfCharExclusive(char c) {
        return lastIndexOfChar(c).map(index -> new Tuple<>(sliceTo(index), sliceFrom(index.next().orElse(end()))));
    }
}
