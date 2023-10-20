package com.meti.compile;

import com.meti.api.collect.Iterator;

public abstract class CompoundLexer<T> implements Lexer<T> {
    abstract Iterator<? extends Lexer<T>> enumerateLexers();

    @Override
    public Iterator<T> lex() {
        return enumerateLexers().flatMap(Lexer::lex);
    }
}
