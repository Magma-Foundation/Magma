package com.meti.compile;

import com.meti.api.collect.Iterator;

public interface Lexer<T> {
    Iterator<T> lex();
}
