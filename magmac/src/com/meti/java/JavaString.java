package com.meti.java;

import com.meti.collect.Index;
import com.meti.collect.Range;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

import java.util.Arrays;

import static com.meti.collect.option.None.None;

public record JavaString(String inner) {
    public Option<Index> firstIndexOfChar(char c) {
        return wrapNegativeIndex(inner.indexOf(c));
    }

    public Option<Index> lastIndexOf(char c) {
        return wrapNegativeIndex(inner.lastIndexOf(c));
    }

    private Option<Index> wrapNegativeIndex(int index) {
        return index == -1
                ? None()
                : Some.Some(new Index(index, inner.length()));
    }

    public JavaString sliceBetween(Range range) {
        return new JavaString(inner.substring(range.start(), range.end()));
    }

    public Stream<JavaString> split(String regex) {
        return Streams.fromList(Arrays.asList(inner.split(regex))).map(JavaString::new);
    }
}
