package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class Input {
    private final String input;

    public Input(String input) {
        this.input = input;
    }

    public boolean contains(String value) {
        return this.input.equals(value);
    }

    Option<Integer> firstChar(char c) {
        var value = compute().indexOf(c);
        return value == -1
                ? new None<>()
                : new Some<>(value);
    }

    public String compute() {
        return input;
    }

    int lastChar() {
        return compute().lastIndexOf(' ');
    }

    Input sliceToEnd(int start) {
        return slice(start, compute().length());
    }

    Input slice(int start, int end) {
        return new Input(compute().substring(start, end).trim());
    }
}
