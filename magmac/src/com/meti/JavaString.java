package com.meti;

public record JavaString(String value) {
    public boolean startsWith(String prefix) {
        return value.startsWith(prefix);
    }

    public Option<Index> indexOfSlice(String slice) {
        return wrapIndex(value.indexOf(slice));
    }

    public Option<Index> indexOfChar(int ch) {
        return wrapIndex(value.indexOf(ch));
    }

    private Option<Index> wrapIndex(int index) {
        return index == -1 ? None.apply() : Some.apply(new Index(index, value.length()));
    }

    public Option<Index> lastIndexOf(int ch) {
        return wrapIndex(value.lastIndexOf(ch));
    }

    public boolean contains(CharSequence s) {
        return value.contains(s);
    }

    public String substring(Index beginIndex) {
        return value.substring(beginIndex.value());
    }

    public String substring(Range range) {
        return value.substring(range.startInclusive(), range.endExclusive());
    }

    public String sliceTo(Index index) {
        return value.substring(0, index.length());
    }
}
