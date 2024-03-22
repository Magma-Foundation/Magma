package com.meti.compile;

import com.meti.collect.stream.Stream;
import com.meti.compile.node.Node;

public interface Lexer {
    Stream<Node> lex();
}
