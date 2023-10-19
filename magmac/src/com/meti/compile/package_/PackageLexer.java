package com.meti.compile.package_;

import com.meti.api.collect.Iterator;
import com.meti.api.collect.JavaString;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.compile.NodeLexer;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;

public record PackageLexer(JavaString stripped) implements NodeLexer {

    private Option<Node> lex1() {
        if (stripped.firstIndexOfSlice("package ").isPresent()) {
            return Some.apply(MapNode.Builder("package").complete());
        } else {
            return None.apply();
        }
    }

    @Override
    public Iterator<Node> lex() {
        return Iterators.fromOption(lex1());
    }
}
