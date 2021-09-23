package com.meti.node;

import com.meti.Input;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import static com.meti.node.ImplicitType.ImplicitType_;

public class DeclarationLexer implements Lexer {
    static final String CONST_PREFIX = "const ";
    static final String LET_PREFIX = "let ";
    private final Input input;

    public DeclarationLexer(Input input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        if (isConstant() || input.startsWithString(LET_PREFIX)) {
            var typeSeparator = input.firstIndexOfChar(':');
            var valueSeparator = input.firstIndexOfChar('=');

            var name = lexName(typeSeparator, valueSeparator);
            var type = lexType(typeSeparator, valueSeparator);
            var value = input.slice(valueSeparator + 1);

            var flag = isConstant() ?
                    Declaration.Flag.CONST :
                    Declaration.Flag.LET;

            var node = new Declaration(flag, name, type, value);
            return new Some<>(node);
        } else {
            return new None<>();
        }
    }

    private boolean isConstant() {
        return input.startsWithString(CONST_PREFIX);
    }

    private String lexName(int typeSeparator, int valueSeparator) {
        var prefix = isConstant() ? CONST_PREFIX : LET_PREFIX;
        var nameEnd = (typeSeparator == -1) ? valueSeparator : typeSeparator;
        return input.slice(prefix.length(), nameEnd);
    }

    private Node lexType(int typeSeparator, int valueSeparator) {
        if (typeSeparator == -1) {
            return ImplicitType_;
        } else {
            var typeString = input.slice(typeSeparator + 1, valueSeparator);
            return new Content(typeString);
        }
    }
}
