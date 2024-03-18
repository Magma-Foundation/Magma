package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;

import static com.meti.collect.option.None.None;

public class VariableLexer implements Lexer {
    private final String exp;

    public VariableLexer(String exp) {
        this.exp = exp;
    }

    private Option<Node> lex0() {
        for (char c : exp.toCharArray()) {
            if(Character.isAlphabetic(c) || Character.isDigit(c)) {
                continue;
            } else {
                return None();
            }
        }

        return Some.Some(new VariableNode(exp));
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}
