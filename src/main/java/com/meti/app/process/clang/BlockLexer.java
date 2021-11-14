package com.meti.app.process.clang;

import com.meti.api.stream.StreamException;
import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.node.Block;
import com.meti.app.node.Content;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredLexer;
import com.meti.app.split.Splitter;

import java.util.ArrayList;
import java.util.List;

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
        return new Block(lexChildren());
    }

    private List<Node> lexChildren() throws CompileException {
        try {
            return new Splitter(new Input(input.slice(1, input.length() - 1))).split()
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .map(Content::new)
                    .foldRight(new ArrayList<Node>(), (collection, node) -> {
                        collection.add(node);
                        return collection;
                    });
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

}
