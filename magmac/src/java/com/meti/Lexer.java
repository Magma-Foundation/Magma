package com.meti;

import java.util.Optional;

public interface Lexer {
    Optional<MapNode> lex();
}
