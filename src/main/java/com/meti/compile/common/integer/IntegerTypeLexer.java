package com.meti.compile.common.integer;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerTypeLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
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
