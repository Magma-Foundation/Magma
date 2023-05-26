package com.meti.node;

import java.util.Optional;

public interface Lexer {
    Optional<Node> lex();
}
