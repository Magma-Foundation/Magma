package com.meti.app.clang;

import com.meti.api.option.Option;

public interface Processor<T> {
    Option<T> process();
}
