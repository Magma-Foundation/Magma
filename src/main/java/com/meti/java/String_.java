package com.meti.java;

import com.meti.core.Option;
import com.meti.iterate.Index;
import com.meti.iterate.Iterator;
import com.meti.iterate.Range;

/**
 * String_ interface provides a contract to handle String-like operations.
 * It is designed to work with custom implementations of String manipulation and querying.
 */
public interface String_ {

    /**
     * Finds the first index of the given character.
     *
     * @param c The character to find.
     * @return The index wrapped in an Option, or None if character is not found.
     */
    Option<Index> firstIndexOfChar(char c);

    /**
     * Returns a slice of the string starting from the given index.
     *
     * @param index The starting index.
     * @return The resulting substring.
     */
    String_ sliceFrom(Index index);

    /**
     * Appends the given string value to the current string.
     *
     * @param value The value to append.
     * @return The new string with the appended value.
     */
    String_ append(String value);

    /**
     * Unwraps the string to its native java.lang.String representation.
     *
     * @return The native string value.
     */
    java.lang.String unwrap();

    /**
     * Checks if the string is empty.
     *
     * @return True if the string is empty, otherwise false.
     */
    boolean isEmpty();

    /**
     * Returns a slice of the string up to the given index.
     *
     * @param index The end index.
     * @return The resulting substring.
     */
    String_ sliceTo(Index index);

    /**
     * Appends the given String_ child to the current string.
     *
     * @param child The String_ child to append.
     * @return The new string with the appended child.
     */
    String_ appendOwned(String_ child);

    /**
     * Finds the first index of the given slice.
     *
     * @param slice The slice to find.
     * @return The range wrapped in an Option, or None if slice is not found.
     */
    Option<Range> firstIndexOfSlice(java.lang.String slice);

    /**
     * Returns a slice of the string between the given range.
     *
     * @param range The range to slice between.
     * @return The resulting substring.
     */
    String_ sliceBetween(Range range);

    /**
     * Finds the last index of the given character.
     *
     * @param c The character to find.
     * @return The index wrapped in an Option, or None if character is not found.
     */
    Option<Index> lastIndexOfChar(char c);

    /**
     * Splits the string by the given regular expression.
     *
     * @param regex The regular expression to split by.
     * @return An iterator over the split elements.
     */
    Iterator<String_> split(String regex);

    /**
     * Prepends the given prefix to the string.
     *
     * @param prefix The prefix to prepend.
     * @return The new string with the prepended prefix.
     */
    String_ prepend(String prefix);

    /**
     * Checks if the string is equal to another String_ instance.
     *
     * @param other The other String_ instance to compare.
     * @return True if the strings are equal, otherwise false.
     */
    boolean equalsTo(String_ other);

    /**
     * Strips whitespace from both ends of the string.
     *
     * @return The stripped string.
     */
    String_ strip();

    /**
     * Checks if the string starts with the given slice.
     *
     * @param slice The slice to check.
     * @return True if the string starts with the slice, otherwise false.
     */
    boolean startsWith(String slice);

    /**
     * Returns an iterator over the characters in the string.
     *
     * @return An iterator over the characters.
     */
    Iterator<Character> iter();

    /**
     * Returns the length of the string.
     *
     * @return The length of the string.
     */
    int length();
}
