package com.meti.app.compile.node;

import com.meti.api.option.Option;

public interface Text {
    Text append(String slice);

    Text append(Text other);

    Text appendRaw(String suffix);

    char apply(int index);

    String compute();

    String computeTrimmed();

    boolean containsChar(char c);

    boolean endsWithChar(char c);

    Option<Integer> firstIndexOfChar(char c);

    Option<Integer> firstIndexOfCharWithOffset(char c, int offset);

    Option<Integer> firstIndexOfSlice(String slice);

    Option<Integer> firstIndexOfSlice(String slice, int offset);

    boolean hasContent();

    boolean isEmpty();

    Option<Integer> lastIndexOfChar(char c);

    Text prepend(String prefix);

    int size();

    Text slice(int start, int end);

    Text slice(int offset);

    boolean startsWithChar(char c);

    boolean startsWithSlice(String slice);
}
