package com.meti.api.collect;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record JavaString(String value) {
    public static final JavaString Empty = new JavaString("");

    public static JavaString apply(String slice) {
        return new JavaString(slice);
    }

    @Override
    public String toString() {
        return value;
    }

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

    public JavaString sliceTo(Index index) {
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

    public boolean equalsToSlice(String value) {
        return this.value.equals(value);
    }

    public boolean isBlank() {
        return this.value.isBlank();
    }

    public JavaString append(JavaString suffix) {
        return new JavaString(this.value + suffix.value);
    }

    public JavaString truncate(int length) {
        if (length <= this.length()) {
            return new JavaString(this.value.substring(0, length));
        } else {
            return this;
        }
    }

    public JavaString replace(String regex, String replacement) {
        return new JavaString(this.value.replaceAll(regex, replacement));
    }

    public Iterator<Index> indices() {
        return new AbstractIterator<Index>() {
            private int current = 0;

            @Override
            public Option<Index> head() {
                var length = JavaString.this.value.length();
                if (current < length) {
                    var copy = current;
                    current++;
                    return Some.apply(new Index(copy, length));
                } else {
                    return None.apply();
                }
            }
        };
    }

    public Option<Index> indexOf(int value) {
        if (value < this.value.length()) {
            return Some.apply(new Index(value, this.value.length()));
        } else {
            return None.apply();
        }
    }

    public Iterator<Index> indicesOfChar(char c) {
        var indices = ImmutableLists.<Index>empty();
        var length = this.value.length();
        for (int i = 0; i < length; i++) {
            if (this.value.charAt(i) == c) {
                indices = indices.addLast(new Index(i, length));
            }
        }
        return indices.iter();
    }
}
