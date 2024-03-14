package com.meti.compile.scope;

import com.meti.collect.option.Option;
import com.meti.collect.option.Options;
import com.meti.compile.Lexer;
import com.meti.compile.attempt.TryNode;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$Option;

public record ReturnLexer(JavaString input) implements Lexer {
    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var index = input.firstIndexOfSlice("return ").$();
            if(!index.isStart()) Options.$$();

            var content = index.next("return ".length()).$();
            return new ReturnNode(new Content(input.sliceFrom(content), 0));
        });
    }
}
