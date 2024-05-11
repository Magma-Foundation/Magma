package com.meti.api.result;

import com.meti.api.option.Option;

public interface Result<T, R> {
    Option<T> findValue();
}
