package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RootInput implements Input {
    private final String input;

    @Override
    public String compute() {
        return input;
    }

    @Override
    public Option<Integer> firstChar(char c) {
        var value = input.indexOf(c);
        return value == -1
                ? new None<>()
                : new Some<>(value);
    }

    @Override
    public int lastChar() {
        return input.lastIndexOf(' ');
    }

    @Override
    public Input slice(int start, int end) {
        return new RootInput(input.substring(start, end).trim());
    }

    @Override
    public Input sliceToEnd(int start) {
        return slice(start, input.length());
    }

    @Override
    public List<Input> split(String delimiter) {
        return Arrays.stream(input.split(delimiter))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(RootInput::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean wraps(String value) {
        return this.input.equals(value);
    }

    public RootInput(String input) {
        this.input = input;
    }
}
