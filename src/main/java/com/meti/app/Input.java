package com.meti.app;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record Input(String value) {
    public char apply(int index) {
        return value.charAt(index);
    }

    public boolean containsChar(char c) {
        return value.indexOf(c) != -1;
    }

    public boolean endsWithChar(char c) {
        return !value.isEmpty() && value.charAt(value.length() - 1) == c;
    }

    public Option<Integer> firstChar(char c) {
        var index = value.indexOf(c);
        if (index == -1) return new None<>();
        else return new Some<>(index);
    }

    public Option<Integer> firstSlice() {
        return new Some<>(value.indexOf("=>"));
    }

    public int length() {
        return value.length();
    }

    public String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    public boolean startsWithChar(char c) {
        return !value.isEmpty() && value.charAt(0) == c;
    }

    public boolean startsWithSlice(String slice) {
        return value.startsWith(slice);
    }
}
