package com.meti.app.process.magma;

import com.meti.app.Input;
import com.meti.app.node.Content;
import com.meti.app.node.Function;
import com.meti.app.node.Node;
import com.meti.app.process.FilteredLexer;

class MagmaFunctionLexer extends FilteredLexer {
    public MagmaFunctionLexer(Input input) {
        super(input);
    }

    @Override
    protected boolean isValid() {
        return input.startsWithSlice("def ");
    }

    @Override
    protected Node processValid() {
        var returnSeparator = input.firstSlice().orElse(-1);
        var identity = new Content(input.slice(0, returnSeparator));
        var body = new Content(input.slice(returnSeparator + "=>".length(), input.length()));
        return new Function(identity, body);
    }
}
