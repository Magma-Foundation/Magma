package com.meti.lex;

import com.meti.Node;

import java.util.Optional;

public interface Lexer {
    Optional<Node> lex();
}
