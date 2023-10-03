package com.meti.compile;

import com.meti.api.option.Option;
import com.meti.compile.node.Node;

public interface Lexer {
    Option<Node> lex();
}
