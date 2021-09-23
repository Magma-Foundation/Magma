package com.meti.node;

import com.meti.option.Option;

public interface Lexer {
    Option<Node> lex();
}
