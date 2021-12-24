package com.meti.compile;

import com.meti.IndexException;
import com.meti.option.Option;

import java.util.List;

public interface Input {
    String compute();

    Option<Integer> firstChar(char c);

    Option<Integer> lastChar();

    int length();

    Input slice(int start, int end) throws IndexException;

    Input sliceToEnd(int start) throws IndexException;

    List<Input> split(String delimiter);

    boolean startsWithSlice(String slice);

    boolean wraps(String value);
}
