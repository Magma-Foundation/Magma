package com.meti.compile.node;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record Text(String value) {
    public Text append(String slice) {
        return new Text(computeTrimmed() + slice);
    }

    public Text append(Text other) {
        return new Text(computeTrimmed() + other.computeTrimmed());
    }

    public char apply(int index) {
        return computeTrimmed().charAt(index);
    }

    public boolean containsChar(char c) {
        return computeTrimmed().indexOf(c) != -1;
    }

    public boolean endsWithChar(char c) {
        var trimmed = computeTrimmed();
        return trimmed.length() != 0 && trimmed.charAt(trimmed.length() - 1) == c;
    }

    public Option<Integer> firstIndexOfChar(char c) {
        var index = computeTrimmed().indexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    public boolean isEmpty() {
        return computeTrimmed().length() == 0;
    }

    public Option<Integer> lastIndexOfChar(char c) {
        var index = computeTrimmed().lastIndexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    public Text prepend(String prefix) {
        return new Text(prefix + computeTrimmed());
    }

    public int size() {
        return computeTrimmed().length();
    }

    public Text slice(int start, int end) {
        return new Text(computeTrimmed().substring(start, end));
    }

    public String compute(){
        return value;
    }

    public String computeTrimmed() {
        return value.trim();
    }

    public Text slice(int offset) {
        return new Text(computeTrimmed().substring(offset));
    }

    public boolean startsWithChar(char c) {
        return hasContent() && computeTrimmed().indexOf(c) == 0;
    }

    private boolean hasContent() {
        return computeTrimmed().length() != 0;
    }

    public boolean startsWithSlice(String slice) {
        return computeTrimmed().startsWith(slice);
    }
}
