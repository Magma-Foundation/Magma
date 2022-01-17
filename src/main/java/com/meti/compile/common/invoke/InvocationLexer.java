package com.meti.compile.common.invoke;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.collect.Streams;
import com.meti.compile.CompileException;
import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.Option;

public record InvocationLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() throws CompileException {
        return text.firstIndexOfChar('(')
                .flatMap(start -> text.lastIndexOfChar(')')
                        .filter(end -> end >= start)
                        .map(end -> extract(start, end)));
    }

    private Invocation extract(Integer start, Integer end) throws CompileException {
        try {
            var callerText = text.slice(0, start);
            var arguments = Streams.apply(text.slice(start + 1, end)
                    .computeTrimmed()
                    .split(","))
                    .filter(s -> !s.isBlank())
                    .map(Text::new)
                    .map(Content::new)
                    .foldRight(new JavaList<Node>(), JavaList::add);
            var caller = new Content(callerText);
            return new Invocation(caller, arguments);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
