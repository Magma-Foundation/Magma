package com.meti.compile;

import com.meti.api.option.Option;

public interface Lexer<T> {
    Option<T> lex();
}
