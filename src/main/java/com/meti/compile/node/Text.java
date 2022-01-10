package com.meti.compile.node;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public final class Text {
    private final String value;
    public final String trimmedValue;

    public Text(String value) {
        this.value = value;
        trimmedValue = value.trim();
    }

    public Text append(String slice) {
        return new Text(trimmedValue + slice);
    }

    public Text append(Text other) {
        return new Text(trimmedValue + other.trimmedValue);
    }

    public char apply(int index) {
        return trimmedValue.charAt(index);
    }

    public String compute() {
        return value;
    }

    public boolean containsChar(char c) {
        return trimmedValue.indexOf(c) != -1;
    }

    public boolean endsWithChar(char c) {
        var trimmed = trimmedValue;
        return trimmed.length() != 0 && trimmed.charAt(trimmed.length() - 1) == c;
    }

    public Option<Integer> firstIndexOfChar(char c) {
        var index = trimmedValue.indexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    public Option<Integer> firstIndexOfSlice(String slice) {
        var index = trimmedValue.indexOf(slice);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    @Deprecated
    public String getTrimmedValue() {
        return trimmedValue;
    }

    public boolean isEmpty() {
        return trimmedValue.length() == 0;
    }

    public Option<Integer> lastIndexOfChar(char c) {
        var index = trimmedValue.lastIndexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    public Text prepend(String prefix) {
        return new Text(prefix + trimmedValue);
    }

    public int size() {
        return trimmedValue.length();
    }

    public Text slice(int start, int end) {
        return new Text(trimmedValue.substring(start, end));
    }

    public Text slice(int offset) {
        return new Text(trimmedValue.substring(offset));
    }

    public boolean startsWithChar(char c) {
        return hasContent() && trimmedValue.indexOf(c) == 0;
    }

    private boolean hasContent() {
        return trimmedValue.length() != 0;
    }

    public boolean startsWithSlice(String slice) {
        return trimmedValue.startsWith(slice);
    }
}
