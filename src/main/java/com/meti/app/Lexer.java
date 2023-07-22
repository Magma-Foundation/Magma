package com.meti.app;

import com.meti.core.Option;

public interface Lexer {
    Option<Renderable> lex();
}
