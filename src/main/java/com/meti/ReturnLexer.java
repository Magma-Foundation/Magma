package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class ReturnLexer extends AbstractLexer {
    public ReturnLexer(Input input) {
        super(input);
    }

    @Override
    public Option<Node> process() throws LexException {
        if (input.startsWithSlice("return ")) {
            try {
                var valueString = this.input.sliceToEnd("return ".length());
                var value = new Content(valueString);
                return new Some<>(new Return(value));
            } catch (IndexException e) {
                throw new LexException(e);
            }
        }
        return new None<>();
    }
}
