package com.meti;

import com.meti.option.Option;

public interface Processor<T> {
    Option<T> process() throws CompileException;
}
