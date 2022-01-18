package com.meti.app.compile.text;

import com.meti.api.option.Option;

public record SlicedText(String root, int from, int to) implements Text {
    @Override
    public Text appendSlice(String slice) {
        return null;
    }

    @Override
    public Text appendText(Text other) {
        return null;
    }

    @Override
    public char apply(int index) {
        return 0;
    }

    @Override
    public String compute() {
        return null;
    }

    @Override
    public String computeTrimmed() {
        return null;
    }

    @Override
    public boolean containsChar(char c) {
        return false;
    }

    @Override
    public boolean endsWithChar(char c) {
        return false;
    }

    @Override
    public Option<Integer> firstIndexOfChar(char c) {
        return null;
    }

    @Override
    public Option<Integer> firstIndexOfCharWithOffset(char c, int offset) {
        return null;
    }

    @Override
    public Option<Integer> firstIndexOfSlice(String slice) {
        return null;
    }

    @Override
    public Option<Integer> firstIndexOfSliceWithOffset(String slice, int offset) {
        return null;
    }

    @Override
    public boolean hasContent() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Option<Integer> lastIndexOfChar(char c) {
        return null;
    }

    @Override
    public Text prepend(String prefix) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Text slice(int start, int end) {
        return null;
    }

    @Override
    public Text slice(int offset) {
        return null;
    }

    @Override
    public boolean startsWithChar(char c) {
        return false;
    }

    @Override
    public boolean startsWithSlice(String slice) {
        return false;
    }
}
