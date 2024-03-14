package com.meti.compile.external;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.collect.option.Some;
import com.meti.compile.Lexer;
import com.meti.compile.node.Node;

public record PackageLexer(String input
) implements Lexer {
    @Override
    public Option<Node> lex() {
        return input.startsWith("package ")
                ? Some.Some(new PackageNode())
                : None.None();
    }
}
