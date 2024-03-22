package com.meti.collect;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Stream;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

public record Index(int value, int length) {
    public Option<Index> next() {
        return next(1);
    }

    public Option<Index> next(int more) {
        var next = value + more;
        if (isWithinBounds(next)) {
            return Some(new Index(next, length));
        } else {
            return None();
        }
    }

    private boolean isWithinBounds(int index) {
        return index <= length;
    }

    public Option<Range> to(Index end) {
        var otherValue = end.value;
        if(value <= otherValue) {
            return Some(new Range(value, otherValue, length));
        } else {
            return None();
        }
    }

    public boolean isStart() {
        return this.value == 0;
    }

    public Index max(Index other) {
        return new Index(Math.max(this.value, other.value), length);
    }

    public boolean isEnd() {
        return this.value == this.length - 1;
    }

    public Option<Range> extend(int length) {
        var next = this.value + length;
        if(isWithinBounds(next)) {
            return Some(new Range(this.value, next, this.length));
        } else {
            return None();
        }
    }

    public Stream<Index> streamFurther() {
        return null;
    }
}
