package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class FunctionLexer {
    private final Input input1;

    public FunctionLexer(Input input1) {
        this.input1 = input1;
    }

    Option<Node> lexFunction() {
        if (input1.getInput().startsWith("def ")) {
            var paramStart = input1.getInput().indexOf('(');
            var name = new Input(input1.getInput()).slice("def ".length(), paramStart).getInput();
            var typeSeparator = input1.getInput().indexOf(':');
            var valueSeparator = input1.getInput().indexOf("=>");
            var typeString = new Input(input1.getInput()).slice(typeSeparator + 1, valueSeparator).getInput();
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
