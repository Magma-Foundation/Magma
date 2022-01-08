package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.ArrayList;

public class BlockLexer {
    private final Input input;

    public BlockLexer(Input input) {
        this.input = input;
    }

    Option<Node> lexBlock() {
        if (input.startsWithChar() && input.getInput().endsWith("}")) {
            var lines = input.slice(1, input.getInput().length() - 1).getInput().split(";");
            var values = new ArrayList<Node>();
            for (String line : lines) {
                if (!line.isBlank()) {
                    values.add(new Content(line));
                }
            }
            return new Some<>(new Block(values));
        }
        return new None<>();
    }
}
