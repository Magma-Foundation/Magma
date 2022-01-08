package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class ReturnLexer {
    private final Input input1;

    public ReturnLexer(Input input1) {
        this.input1 = input1;
    }

    Option<Node> lexReturn() {
        if (getInput1().getInput().startsWith("return ")) {
            var valueString = new Input(getInput1().getInput()).slice("return ".length(), getInput1().getInput().length()).getInput();
            var value = new Content(valueString);
            return new Some<>(new Return(value));
        }
        return new None<>();
    }

    public Input getInput1() {
        return input1;
    }
}
