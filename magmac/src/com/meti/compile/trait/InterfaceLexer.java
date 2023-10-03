package com.meti.compile.trait;

import com.meti.api.collect.JavaString;
import com.meti.api.option.Option;
import com.meti.api.option.Options;
import com.meti.compile.Lexer;
import com.meti.compile.node.MapNode;
import com.meti.compile.node.Node;

public record InterfaceLexer(JavaString stripped) implements Lexer {

    @Override
    public Option<Node> lex() {
        return Options.$Option(() -> {
            var nameStart = stripped.firstIndexOfChar('<').$();
            var keywordEnd = stripped.firstIndexOfSlice("interface ").$()
                    .nextBy("interface ".length()).$();
            var name = stripped.sliceBetween(keywordEnd.to(nameStart).$()).strip();
            return MapNode.Builder(new JavaString("interface"))
                    .withString(JavaString.apply("name"), name)
                    .complete();
        });
    }
}
