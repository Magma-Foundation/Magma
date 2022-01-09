package com.meti.compile;

import com.meti.compile.node.Content;
import com.meti.compile.node.Input;
import com.meti.compile.node.Node;
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
            var returnType = new Content(input.slice(typeSeparator + 1, valueSeparator));
            var value = new Content(input.slice(valueSeparator + 2));
            var identity = new Field(name, returnType);
            return new Some<>(new Function(identity, value));
        }
        return new None<>();
    }

}
