package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class IntegerLexer {
    private final Input input1;

    public IntegerLexer(Input input1) {
        this.input1 = input1;
    }

    Option<Node> lexInteger() {
        try {
            Integer.parseInt(getInput1().getInput());
            return new Some<>(new Content(getInput1().getInput()));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }

    public Input getInput1() {
        return input1;
    }
}
