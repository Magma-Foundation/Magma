package com.meti.java;

import com.meti.core.Option;
import com.meti.iterate.Index;
import com.meti.iterate.Iterator;

public interface String_ {
    Option<Index> firstIndexOfChar(char c);

    String_ sliceFrom(Index index);

    String_ append(String value);

    java.lang.String unwrap();

    boolean isEmpty();

    String_ sliceTo(Index index);

    String_ appendOwned(String_ child);

    Option<Index> firstIndexOfSlice(java.lang.String slice);

    String_ sliceBetween(Index start, Index end);

    Option<Index> lastIndexOfChar(char c);

    Iterator<String_> split(String regex);

    String_ prepend(String prefix);

    int compareTo(String_ other);

    boolean equalsTo(String_ other);
}
