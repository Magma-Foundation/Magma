package com.meti.compile;

import com.meti.api.Index;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public record JavaString(String unwrap) {
    Option<Index> firstIndexOfChar(char c) {
        var index = this.unwrap.indexOf(c);
        return index == -1
                ? new None<>()
                : new Some<>(new Index(index));
    }

    JavaString strip() {
        return new JavaString(unwrap.strip());
    }
}