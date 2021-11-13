package com.meti.app.process;

import com.meti.api.option.Option;
import com.meti.app.CompileException;

public interface Processor<T> {
    Option<T> process() throws CompileException;
}
