package com.meti.app.compile;

import com.meti.core.Option;

public interface Lexer {
    Option<Node> lex();
}
