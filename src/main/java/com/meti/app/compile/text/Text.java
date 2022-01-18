package com.meti.app.compile.text;

import com.meti.api.collect.IndexException;
import com.meti.api.option.Option;

public interface Text extends Output {
    char apply(int index) throws IndexException;

    /**
     * Returns true if this character is contained within the trimmed value of this text,
     * otherwise false.
     * @param c The character for which to search.
     * @return Whether the character is contained within this text.
     */
    boolean containsChar(char c);

    /**
     * Returns whether if this trimmed text by the given character.
     * If the text is empty, then false is returned.
     * @param c The character.
     * @return Whether the character ends this text.
     */
    boolean endsWithChar(char c);

    Option<Integer> firstIndexOfChar(char c);

    Option<Integer> firstIndexOfCharWithOffset(char c, int offset);

    Option<Integer> firstIndexOfSlice(String slice);

    Option<Integer> firstIndexOfSliceWithOffset(String slice, int offset);

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
