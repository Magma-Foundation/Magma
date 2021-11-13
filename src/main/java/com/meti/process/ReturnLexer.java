package com.meti.process;

import com.meti.CompileException;
import com.meti.Input;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.node.Return;

public class ReturnLexer extends FilteredLexer {
    public ReturnLexer(Input input) {
        super(input);
    }

    @Override
    protected boolean isValid() {
        return input.startsWithSlice("return ");
    }

    @Override
    protected Node processValid() throws CompileException {
        return new Return(new Content(input.slice("return ".length(), input.length())));
    }
}
