package com.meti.clang;

import com.meti.option.Option;

public interface Processor<T> {
    Option<T> process();
}
