package com.meti.feature;

import com.meti.Input;
import com.meti.clang.AbstractLexer;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockLexer extends AbstractLexer {
    public BlockLexer(Input input) {
        super(input);
    }

    @Override
    protected boolean isValid() {
        return input.startsWithChar('{') && input.endsWithChar('}');
    }

    @Override
    protected Node processValid() {
        return new Block(Arrays.stream(input.slice(1, input.length() - 1).split(";"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(Input::new)
                .map(Content::new)
                .collect(Collectors.toList()));
    }
}
