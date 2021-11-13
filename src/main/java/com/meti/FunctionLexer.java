package com.meti;

class FunctionLexer extends AbstractProcessor<Node> {
    private final Input input;

    public FunctionLexer(Input input) {
        this.input = input;
    }

    @Override
    protected boolean isValid() {
        return input.startsWithSlice();
    }

    @Override
    protected Node processValid() {
        var returnSeparator = input.firstIndexOfSlice().orElse(-1);
        var identity = new Content(input.slice(0, returnSeparator));
        var body = new Content(input.slice(returnSeparator + "=>".length(), input.length()));
        return new Function(identity, body);
    }
}
