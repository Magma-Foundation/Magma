package com.meti.compile.procedure;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public final class ConstructionLexer implements Lexer {
    private final JavaString stripped;

    public ConstructionLexer(JavaString javaString) {
        this.stripped = javaString;
    }

    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var prefix = stripped.firstIndexOfSlice("new ").$();
            if (!prefix.isStart()) $$();

            var prefixIndex = prefix.next("new ".length()).$();
            var start = stripped.firstIndexOfChar('(').$();
            var end = stripped.lastIndexOf(')').$();

            var caller = new Content(stripped.sliceBetween(prefixIndex.to(start).$()), 0);
            var list = stripped.sliceBetween(start.next().$().to(end).$())
                    .split(",")
                    .map(arg -> new Content(arg, 0))
                    .collect(Collectors.toList());

            return new ConstructionNode(caller, list);
        });
    }
}