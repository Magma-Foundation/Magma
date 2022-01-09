package com.meti.compile.node;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record Text(String value) {
    public Text(String value) {
        this.value = value.trim();
    }

    public Text append(String slice) {
        return new Text(value + slice);
    }

    public Text append(Text other) {
        return new Text(value + other.value);
    }

    public Option<Integer> firstIndexOfChar(char c) {
        var index = value.indexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    public boolean isEmpty() {
        return value.length() == 0;
    }

    public Text prepend(String prefix) {
        return new Text(prefix + value);
    }

    public Text slice(int start, int end) {
        return new Text(compute().substring(start, end));
    }

    public String compute() {
        return value;
    }

    public Text slice(int offset) {
        return new Text(value.substring(offset));
    }

    public boolean startsWithChar(char c) {
        return hasContent() && compute().indexOf(c) == 0;
    }

    private boolean hasContent() {
        return compute().length() != 0;
    }

    public boolean startsWithSlice(String slice) {
        return value.startsWith(slice);
    }
}
