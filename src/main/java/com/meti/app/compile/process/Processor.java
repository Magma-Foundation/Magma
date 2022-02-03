package com.meti.app.compile.process;

import com.meti.api.option.Option;
import com.meti.app.compile.stage.CompileException;

public interface Processor<T> {
    Option<T> process() throws CompileException;
}
