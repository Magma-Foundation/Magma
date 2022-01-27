package com.meti.app.compile.text;

import com.meti.api.core.F1;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.Objects;

public final class RootText implements Input {
    public final String trimmedValue;
    private final String value;

    public RootText(String value) {
        this.value = value;
        trimmedValue = value.trim();
    }

    @Override
    public char apply(int index) {
        return trimmedValue.charAt(index);
    }

    @Override
    public boolean containsChar(char c) {
        return trimmedValue.indexOf(c) != -1;
    }

    @Override
    public boolean endsWithChar(char c) {
        var trimmed = trimmedValue;
        return trimmed.length() != 0 && trimmed.charAt(trimmed.length() - 1) == c;
    }

    @Override
    public boolean equalsInput(Input other) {
        return other.equalsSlice(trimmedValue);
    }

    @Override
    public boolean equalsSlice(String slice) {
        return this.trimmedValue.equals(slice);
    }

    @Override
    public Option<Integer> firstIndexOfChar(char c) {
        var index = trimmedValue.indexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    @Override
    public Option<Integer> firstIndexOfCharWithOffset(char c, int offset) {
        var index = trimmedValue.indexOf(c, offset);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    @Override
    public Option<Integer> firstIndexOfSlice(String slice) {
        var index = trimmedValue.indexOf(slice);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    @Override
    public Option<Integer> firstIndexOfSliceWithOffset(String slice, int offset) {
        var index = trimmedValue.indexOf(slice, offset);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    private boolean hasContent() {
        return trimmedValue.length() != 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trimmedValue, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootText text)) return false;
        return Objects.equals(trimmedValue, text.trimmedValue);
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public boolean isEmpty() {
        return trimmedValue.length() == 0;
    }

    @Override
    public Output toOutput() {
        return new StringOutput(trimmedValue);
    }

    @Override
    public Option<Integer> lastIndexOfChar(char c) {
        var index = trimmedValue.lastIndexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(index);
    }

    @Override
    public <T, E extends Exception> T map(F1<String, T, E> mapper) throws E {
        return mapper.apply(trimmedValue);
    }

    @Override
    public int size() {
        return trimmedValue.length();
    }

    @Override
    public RootText slice(int start, int end) {
        return new RootText(trimmedValue.substring(start, end));
    }

    @Override
    public RootText slice(int offset) {
        return new RootText(trimmedValue.substring(offset));
    }

    @Override
    public boolean startsWithChar(char c) {
        return hasContent() && trimmedValue.indexOf(c) == 0;
    }

    @Override
    public boolean startsWithSlice(String slice) {
        return trimmedValue.startsWith(slice);
    }
}
