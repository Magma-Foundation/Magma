package com.meti.compile;

import com.meti.api.option.Option;

public interface Lexer {
    Option<Node> lex();
}
