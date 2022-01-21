package com.meti.app.compile.text;

import com.meti.api.collect.IndexException;
import com.meti.api.core.F1;
import com.meti.api.option.Option;

public interface Input {
    char apply(int index) throws IndexException;

    /**
     * Returns true if this character is contained within the trimmed value of this input,
     * otherwise false.
     *
     * @param c The character for which to search.
     * @return Whether the character is contained within this input.
     */
    boolean containsChar(char c);

    /**
     * Returns whether if this trimmed input by the given character.
     * If the input is empty, then false is returned.
     *
     * @param c The character.
     * @return Whether the character ends this input.
     */
    boolean endsWithChar(char c);

    boolean equalsInput(Input other);

    boolean equalsSlice(String slice);

    Option<Integer> firstIndexOfChar(char c);

    Option<Integer> firstIndexOfCharWithOffset(char c, int offset);

    Option<Integer> firstIndexOfSlice(String slice);

    Option<Integer> firstIndexOfSliceWithOffset(String slice, int offset);

    boolean isEmpty();

    Option<Integer> lastIndexOfChar(char c);

    <T, E extends Exception> T map(F1<String, T, E> mapper) throws E;

    int size();

    RootText slice(int start, int end);

    RootText slice(int offset);

    boolean startsWithChar(char c);

    boolean startsWithSlice(String slice);

    Output toOutput();
}
