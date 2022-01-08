package com.meti.compile;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record ReturnLexer(Input input) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (input.getInput().startsWith("return ")) {
            var valueString = new Input(input.getInput()).slice("return ".length(), input.getInput().length()).getInput();
            var value = new Content(valueString);
            return new Some<>(new Return(value));
        }
        return new None<>();
    }
}
