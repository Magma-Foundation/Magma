package com.meti.compile;

import com.meti.IndexException;
import com.meti.option.None;
import com.meti.option.Option;

import java.util.Collections;
import java.util.List;

public class EmptyInput implements Input {
    public static final Input EmptyInput_ = new EmptyInput();

    public EmptyInput() {
    }

    @Override
    public String compute() {
        return "";
    }

    @Override
    public Option<Integer> firstChar(char c) {
        return new None<>();
    }

    @Override
    public Option<Integer> lastChar() {
        return new None<>();
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public Input slice(int start, int end) throws IndexException {
        if (start == 0 && end == 0) return this;
        throw new IndexException("Input is empty.");
    }

    @Override
    public Input sliceToEnd(int start) throws IndexException {
        if (start == 0) return this;
        throw new IndexException("Input is empty.");
    }

    @Override
    public List<Input> split(String delimiter) {
        return Collections.emptyList();
    }

    @Override
    public boolean startsWithSlice(String slice) {
        return slice.isEmpty();
    }

    @Override
    public boolean wraps(String value) {
        return value.isEmpty();
    }
}
