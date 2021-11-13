package com.meti;

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
