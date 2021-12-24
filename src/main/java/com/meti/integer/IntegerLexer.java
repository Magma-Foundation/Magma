package com.meti.integer;

import com.meti.AbstractLexer;
import com.meti.Input;
import com.meti.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class IntegerLexer extends AbstractLexer {
    public IntegerLexer(Input input) {
        super(input);
    }

    @Override
    public Option<Node> process() {
        try {
            var valueAsString = input.compute();
            var valueAsInteger = Integer.parseInt(valueAsString);
            return new Some<>(new IntegerNode(valueAsInteger));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
