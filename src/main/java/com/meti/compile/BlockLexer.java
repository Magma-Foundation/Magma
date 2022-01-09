package com.meti.compile;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;

public record BlockLexer(Input input) implements Lexer {
    @Override
    public Option<Node> lex() {
        if (input.startsWithChar() && input.getInput().endsWith("}")) {
            var lines = input.slice(1, input.getInput().length() - 1).getInput().split(";");
            var values = new ArrayList<Node>();
            for (String line : lines) {
                if (!line.isBlank()) {
                    values.add(new Content(new Input(line)));
                }
            }
            return new Some<>(new Block(values));
        }
        return new None<>();
    }
}
