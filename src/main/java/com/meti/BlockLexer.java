package com.meti;

import com.meti.node.Block;
import com.meti.node.Content;
import com.meti.node.Node;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockLexer extends FilteredLexer {
    public BlockLexer(Input input) {
        super(input);
    }

    @Override
    protected boolean isValid() {
        return input.startsWithChar('{') && input.endsWithChar('}');
    }

    @Override
    protected Node processValid() throws CompileException {
        var children = Arrays.stream(input.slice(1, input.length() - 1).split(";"))
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(Content::new)
                .collect(Collectors.toList());
        return new Block(children);
    }
}
