package com.meti.compile;

import com.meti.api.collect.JavaString;
import com.meti.api.result.Result;

public interface LexerFactory<T> {
    Result<Lexer<T>, ? extends CompileException> create(JavaString input, JavaString type);
}
