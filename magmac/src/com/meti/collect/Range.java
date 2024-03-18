package com.meti.collect;

import com.meti.collect.option.Option;

public record Range(int start, int end, int length) {
    public Index startIndex() {
        return new Index(start, length);
    }
}
