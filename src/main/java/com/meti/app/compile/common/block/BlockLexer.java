package com.meti.app.compile.common.block;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.AbstractProcessor;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;

public final class BlockLexer extends AbstractProcessor<Input, Node> {
    public BlockLexer(Input input) {
        super(input);
    }

    @Override
    protected boolean validate() {
        return input.startsWithChar('{') && input.endsWithChar('}');
    }

    @Override
    protected Node createNode() throws CompileException {
        try {
            var body = input.slice(1, input.size() - 1);
            var lines = new Splitter(body)
                    .split()
                    .map(InputNode::new)
                    .foldRight(List.<Node>createList(), List::add);
            return new Block(lines);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
