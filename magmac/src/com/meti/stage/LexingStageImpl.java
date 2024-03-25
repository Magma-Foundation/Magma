package com.meti.stage;

import com.meti.lex.Lexer;

import java.util.function.Function;

public class LexingStageImpl extends LexingStage {
    private final Function<String, Lexer> constructor;

    public LexingStageImpl(Function<String, Lexer> constructor) {
        this.constructor = constructor;
    }

    @Override
    protected Lexer createLexer(String value) {
        return constructor.apply(value);
    }
}
