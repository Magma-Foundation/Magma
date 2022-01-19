package com.meti.app.compile.common.integer;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.process.Processor;
import com.meti.app.compile.text.Input;

public record IntegerTypeLexer(Input text) implements Processor<Node> {
    @Override
    public Option<Node> process() {
        var isSigned = text.startsWithChar('I');
        var isUnsigned = text.startsWithChar('U');
        if (isSigned || isUnsigned) {
            var bitsText = text.slice(1);
            var bits = Integer.parseInt(bitsText.computeTrimmed());
            return new Some<>(new IntegerType(isSigned, bits));
        } else {
            return new None<>();
        }
    }
}
