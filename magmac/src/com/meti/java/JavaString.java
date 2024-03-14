package com.meti.java;

import com.meti.collect.Index;
import com.meti.collect.Range;
import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

import java.util.Arrays;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record JavaString(String inner) {
    @Override
    public String toString() {
        return inner;
    }

    public Option<Index> firstIndexOfChar(char c) {
        return wrapNegativeIndex(inner.indexOf(c));
    }

    public Option<Index> lastIndexOf(char c) {
        return wrapNegativeIndex(inner.lastIndexOf(c));
    }

    private Option<Index> wrapNegativeIndex(int index) {
        return index == -1
                ? None()
                : Some(new Index(index, inner.length()));
    }

    public JavaString sliceBetween(Range range) {
        return new JavaString(inner.substring(range.start(), range.end()));
    }

    public Stream<JavaString> split(String regex) {
        return Streams.fromList(Arrays.asList(inner.split(regex))).map(JavaString::new);
    }

    public JavaString sliceTo(Index end) {
        return new JavaString(this.inner.substring(0, end.value()));
    }

    public JavaString sliceFrom(Index start) {
        return new JavaString(this.inner.substring(start.value()));
    }

    public JavaString strip() {
        return new JavaString(this.inner.strip());
    }

    public boolean startsWithSlice(String slice) {
        return this.inner.startsWith(slice);
    }

    public Option<Index> asIndex(int value) {
        if (value <= this.inner.length()) {
            return Some(new Index(value, this.inner.length()));
        } else {
            return None();
        }
    }

    public Option<JavaString> sliceFromRaw(int index) {
        return asIndex(index).map(this::sliceFrom);
    }
}
