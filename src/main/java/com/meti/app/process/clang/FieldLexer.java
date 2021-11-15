package com.meti.app.process.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.Primitive;
import com.meti.app.node.Node;
import com.meti.app.node.Sequence;
import com.meti.app.process.Lexer;

import java.util.Collections;

public record FieldLexer(Input input) implements Lexer {
    @Override
    public Option<Node> process() throws CompileException {
        if (input.value().startsWith("def ")) {
            var name = input.slice("def ".length(), input.firstChar('(').orElse(-1));
            var type = input.slice(input.firstChar(':').orElse(-1) + 1, input.length());
            return new Some<>(Primitive.valueOf(type).asFieldWithOnset(name, new Sequence(Collections.emptyList())));
        } else {
            return new None<>();
        }
    }
}
