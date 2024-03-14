package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;

import static com.meti.collect.option.None.None;

public class VariableLexer implements Lexer {
    private final String exp;

    public VariableLexer(String exp) {
        this.exp = exp;
    }

    @Override
    public Option<Node> lex() {
        for (char c : exp.toCharArray()) {
            if(Character.isAlphabetic(c) || Character.isDigit(c)) {
                continue;
            } else {
                return None();
            }
        }

        return Some.Some(new VariableNode(exp));
    }
}
