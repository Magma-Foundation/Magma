package com.meti.app.process.clang;

import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.node.Content;
import com.meti.app.node.Node;
import com.meti.app.node.Return;
import com.meti.app.process.FilteredLexer;

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
