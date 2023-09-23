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

    public JavaString sliceFrom(Index beginIndex) {
        return new JavaString(value.substring(beginIndex.value()));
    }

    public String sliceBetween(Range range) {
        return value.substring(range.startInclusive(), range.endExclusive());
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

}
