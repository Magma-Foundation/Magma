package com.meti.app.compile.feature;

import com.meti.app.clang.AbstractProcessor;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BlockLexer extends AbstractProcessor<Node> {
    private final Input input;

    public BlockLexer(Input input) {
        this.input = input;
    }

    @Override
    protected boolean validate() {
        return input.startsWithChar('{') && input.endsWithChar('}');
    }

    @Override
    protected Node processDefined() {
        var split = input.slice(1, input.length() - 1).split(";");
        var lines = createChildren(split);
        return new Block(lines);
    }

    private List<Content> createChildren(String[] split) {
        return Arrays.stream(split)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(Input::new)
                .map(Content::new)
                .collect(Collectors.toList());
    }
}
