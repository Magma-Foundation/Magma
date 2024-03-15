package com.meti.compile.scope;

import com.meti.collect.Index;
import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

public class LambdaLexer implements Lexer {
    private final JavaString input;

    public LambdaLexer(JavaString input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        var index = input.firstIndexOfSlice("->");
        if(index.isPresent()) {
            return Some.Some(new LambdaNode());
        } else {
            return None.None();
        }
    }
}
