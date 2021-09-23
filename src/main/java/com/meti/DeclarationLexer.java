package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class DeclarationLexer implements Lexer {
    static final String CONST_PREFIX = "const ";
    static final String LET_PREFIX = "let ";
    private final Input input;

    public DeclarationLexer(Input input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        if (input.startsWithString(CONST_PREFIX) || input.startsWithString(LET_PREFIX)) {
            var typeSeparator = input.firstIndexOfChar(':');
            var prefix = input.startsWithString(CONST_PREFIX) ? CONST_PREFIX : LET_PREFIX;
            var name = input.slice(prefix.length(), typeSeparator);
            var valueSeparator = input.firstIndexOfChar('=');
            var value = input.slice(valueSeparator + 1);

            var typeString = input.slice(typeSeparator + 1, valueSeparator);
            var type = new Content(typeString);

            return new Some<>(new Declaration(Declaration.Flag.CONST, name, type, value));
        } else {
            return new None<>();
        }
    }
}
