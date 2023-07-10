package com.meti.java;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public final class JavaString {
    private final String value;

    public JavaString(String value) {
        this.value = value;
    }

    public static JavaString empty() {
        return new JavaString("");
    }

    public Option<Index> firstIndexOfChar(char c) {
        var index = this.value.indexOf(c);
        return index == -1
                ? new None<>()
                : Some.apply(new Index(index, this.value.length()));
    }

    public JavaString sliceToEnd(Index index) {
        return new JavaString(value.substring(0, index.unwrap()));
    }

    public JavaString concat(String value) {
        return new JavaString(this.value + value);
    }

    public String unwrap() {
        return value;
    }

    public boolean isEmpty() {
        return this.value.length() == 0;
    }

    public boolean startsWith(String slice) {
        if (slice.length() <= this.value.length()) {
            for (int i = 0; i < slice.length(); i++) {
                if (slice.charAt(i) != this.value.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
