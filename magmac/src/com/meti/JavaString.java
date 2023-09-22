package com.meti;

public record JavaString(String value) {
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

    public String slice(Index beginIndex) {
        return value.substring(beginIndex.value());
    }

    public String slice(Range range) {
        return value.substring(range.startInclusive(), range.endExclusive());
    }

    public String sliceTo(Index index) {
        return value.substring(0, index.length());
    }

    public int length() {
        return value.length();
    }
}
