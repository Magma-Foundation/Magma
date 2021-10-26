package com.meti.app.clang;

import com.meti.api.option.Option;
import com.meti.app.compile.node.attribute.CompileException;

public interface Processor<T> {
    Option<T> process() throws CompileException;
}
