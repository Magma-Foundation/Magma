package com.meti.feature;

import com.meti.feature.Node;
import com.meti.safe.option.Option;

public interface Lexer {
    Option<Node> lex();
}
