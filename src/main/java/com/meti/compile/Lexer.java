package com.meti.compile;

import com.meti.option.Option;

public interface Lexer {
    Option<Node> lex();
}
