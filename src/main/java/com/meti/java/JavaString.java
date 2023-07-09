package com.meti.java;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public record JavaString(String value) {

    public static JavaString empty() {
        return new JavaString("");
    }

    public Option<Index> firstIndexOfChar(char c) {
        var index = this.value.indexOf(c);
        return index == -1
                ? new None<>()
                : Some.apply(new Index(index));
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
}
