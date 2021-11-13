package com.meti.process;

import com.meti.CompileException;
import com.meti.Input;
import com.meti.Primitive;
import com.meti.node.Node;
import com.meti.node.Sequence;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

import java.util.Collections;

public record DeclarationLexer(Input input) implements Lexer {
    @Override
    public Option<Node> process() throws CompileException {
        if (input.value().startsWith("def ")) {
            var name = input.slice("def ".length(), input.firstChar('(').orElse(-1));
            var type = input.slice(input.firstChar(':').orElse(-1) + 1, input.length());
            return new Some<>(Primitive.valueOf(type).asField(name, new Sequence(Collections.emptyList())));
        } else {
            return new None<>();
        }
    }
}
