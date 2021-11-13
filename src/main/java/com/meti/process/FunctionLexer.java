package com.meti.process;

import com.meti.Input;
import com.meti.node.Content;
import com.meti.node.Function;
import com.meti.node.Node;

class FunctionLexer extends FilteredLexer {
    public FunctionLexer(Input input) {
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
