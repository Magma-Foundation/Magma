package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class IntegerLexer extends AbstractLexer {
    public IntegerLexer(Input input) {
        super(input);
    }

    @Override
    public Option<Node> lex() {
        try {
            var valueAsString = input.compute();
            var valueAsInteger = Integer.parseInt(valueAsString);
            return new Some<>(new IntegerNode(valueAsInteger));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
