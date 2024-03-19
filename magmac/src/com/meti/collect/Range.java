package com.meti.collect;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;

public record Range(int start, int end, int length) {
    public Index startIndex() {
        return new Index(start, length);
    }

    public Index endIndex() {
        return new Index(end, length);
    }

    public Option<Range> offsetRight(int offset) {
        if (start + offset <= length && end + offset <= length) {
            return Some.Some(new Range(start + offset, end + offset, length));
        } else {
            return None.None();
        }
    }

    public Stream<Index> stream() {
        return Streams.from(startIndex(), endIndex());
    }
}
