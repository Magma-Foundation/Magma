package com.meti;

public record JavaLexer(JavaString stripped) implements Lexer {
    static Iterator<Lexer> enumerateLexers(JavaString stripped) {
        return Iterators.from(
                new ImportLexer(stripped),
                new BlockLexer(stripped),
                new ClassLexer(stripped),
                new RecordLexer(stripped)
        );
    }

    @Override
    public Option<Node> lex() {
        return enumerateLexers(this.stripped())
                .map(Lexer::lex)
                .head()
                .flatMap(value -> value);
    }
}