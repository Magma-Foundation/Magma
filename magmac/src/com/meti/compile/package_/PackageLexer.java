package com.meti.compile.package_;

import com.meti.api.collect.JavaString;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.Lexer;
import com.meti.compile.MapNode;
import com.meti.compile.Node;

public record PackageLexer(JavaString stripped) implements Lexer {

    @Override
    public Option<Node> lex() {
        if (stripped.firstIndexOfSlice("package ").isPresent()) {
            return Some.apply(new MapNode(new JavaString("package"), attributes));
        } else {
            return None.apply();
        }
    }
}
