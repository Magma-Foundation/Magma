package com.meti;

public record JavaString(String value) {
    private static Option<Index> wrapInIndex(int value) {
        return value == -1
                ? new Some<>(new Index(value))
                : new None<>();
    }

    Option<Index> firstIndexOfChar(char c) {
        return wrapInIndex(this.value().indexOf(c));
    }

    Tuple<JavaString, JavaString> splitBetweenChar(Index index) {
        var parent = new JavaString(value.substring(0, index.value()));
        var substring = new JavaString(value.substring(index.value() + 1));
        return new Tuple<>(parent, substring);
    }

    public Option<Index> lastIndexOfChar(char c) {
        return wrapInIndex(this.value().lastIndexOf(c));
    }
}