package com.meti.compile;

import com.meti.api.collect.Iterator;
import com.meti.api.iterate.Iterators;
import com.meti.api.option.Option;
import com.meti.compile.node.Node;

public abstract class CompoundLexer implements NodeLexer {
    abstract Iterator<? extends NodeLexer> enumerateLexers();

    @Override
    public Option<Node> lex() {
        return enumerateLexers()
                .map(NodeLexer::lex)
                .flatMap(Iterators::fromOption)
                .head();
    }
}
