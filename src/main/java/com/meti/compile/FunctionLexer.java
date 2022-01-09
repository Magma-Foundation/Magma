package com.meti.compile;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionLexer(Input input) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (input.getInput().startsWith("def ")) {
            var paramStart = input.getInput().indexOf('(');
            var name = input.slice("def ".length(), paramStart);
            var typeSeparator = input.getInput().indexOf(':');
            var valueSeparator = input.getInput().indexOf("=>");
            var type2 = new Content(input.slice(typeSeparator + 1, valueSeparator));
            var value = new Content(input.slice(valueSeparator + 2));
            return new Some<>(new Function(name, type2, value));
        }
        return new None<>();
    }

}
