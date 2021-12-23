package com.meti;

import com.meti.option.Option;

public interface Processor<T, E extends CompileException> {
    Option<T> process() throws E;
}
