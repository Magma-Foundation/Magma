package com.meti.app.compile;

import com.meti.core.Option;
import com.meti.core.Result;

public interface Lexer {
    Option<Result<Node, CompileException>> lex();
}
