package com.meti;

import com.meti.option.Option;

public interface Lexer {
    Option<Node> lex() throws LexException;
}
