package com.meti;

public interface Processor<T> {
    Option<T> process() throws CompileException;
}
