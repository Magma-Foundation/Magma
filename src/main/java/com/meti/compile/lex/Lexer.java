package com.meti.compile.lex;

import com.meti.compile.node.Node;
import com.meti.option.Option;

public interface Lexer {
    Option<Node> lex();
}
