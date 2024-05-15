package com.meti.java;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record JavaString(String input) {
    private static Option<Integer> wrapIndex(int index) {
        return index == -1 ? new None<>() : new Some<>(index);
    }

    public Option<Integer> lastIndexOfChar(char c) {
        return wrapIndex(this.input().lastIndexOf(c));
    }

    public Option<Integer> firstIndexOfChar(char c) {
        return wrapIndex(this.input().indexOf(c));
    }
}