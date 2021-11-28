package com.meti.app.process.clang;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.CompileException;
import com.meti.app.Input;
import com.meti.app.node.IntegerNode;
import com.meti.app.node.Node;
import com.meti.app.process.Lexer;

public record IntLexer(Input input) implements Lexer {
    @Override
    public Option<Node> process() throws CompileException {
        try {
            return new Some<>(new IntegerNode(Integer.parseInt(input.value())));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
