package com.meti.compile.common.invoke;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.Option;

import java.util.Arrays;
import java.util.stream.Collectors;

public record InvocationLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.firstIndexOfChar('(')
                .flatMap(start -> text.firstIndexOfChar(')')
                .filter(end -> end >= start)
                .map(end -> extract(start, end)));
    }

    private Invocation extract(Integer start, Integer end) {
        var callerText = text.slice(0, start);
        var arguments = Arrays.stream(text.slice(start + 1, end)
                .computeTrimmed()
                .split(","))
                .filter(s -> !s.isBlank())
                .map(Text::new)
                .map(Content::new)
                .collect(Collectors.<Node>toList());
        var caller = new Content(callerText);
        return new Invocation(caller, arguments);
    }
}
