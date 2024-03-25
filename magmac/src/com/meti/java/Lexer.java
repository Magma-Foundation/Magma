package com.meti.java;

import com.meti.node.Node;

import java.util.Optional;

public interface Lexer {
    Optional<Node> lex();
}
