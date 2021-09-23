package com.meti.node;

import com.meti.Input;
import com.meti.Splitter;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.stream.Collectors;

public class BlockLexer implements Lexer {
    private final Input input;

    public BlockLexer(Input input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        if (input.startsWithString("{")) {
            var start = input.firstIndexOfChar('{');
            var end = input.firstIndexOfChar('}');
            var body = input.slice(start + 1, end);
            var children = new Splitter(body)
                    .split()
                    .stream()
                    .map(Content::new)
                    .collect(Collectors.toList());
            return new Some<>(new Block(children));
        }
        return new None<>();
    }
}
