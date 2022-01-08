package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record FunctionLexer(Input input) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (input.getInput().startsWith("def ")) {
            var paramStart = input.getInput().indexOf('(');
            var name = new Input(input.getInput()).slice("def ".length(), paramStart).getInput();
            var typeSeparator = input.getInput().indexOf(':');
            var valueSeparator = input.getInput().indexOf("=>");
            var typeString = new Input(input.getInput()).slice(typeSeparator + 1, valueSeparator).getInput();
            String type;
            if (typeString.equals("I16")) {
                type = "int";
            } else {
                type = "unsigned int";
            }
            return new Some<>(new Content(type + " " + name + "(){return 0;}"));
        }
        return new None<>();
    }
}
