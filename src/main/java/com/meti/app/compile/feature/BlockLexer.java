package com.meti.app.compile.feature;

import com.meti.api.ArrayStream;
import com.meti.api.StreamException;
import com.meti.app.clang.AbstractProcessor;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

import java.util.ArrayList;
import java.util.List;

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
    protected Node processDefined() throws CompileException {
        try {
            var split = input.slice(1, input.length() - 1).split(";");
            var lines = createChildren(split);
            return new Block(lines);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private List<Content> createChildren(String[] split) throws StreamException {
        return new ArrayStream<>(split)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(Input::new)
                .map(Content::new)
                .foldRight(new ArrayList<>(), (contents, content) -> {
                    contents.add(content);
                    return contents;
                });
    }
}
