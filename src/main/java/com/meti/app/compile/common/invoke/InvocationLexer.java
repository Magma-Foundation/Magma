package com.meti.app.compile.common.invoke;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.option.Option;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.Input;
import com.meti.app.compile.text.RootText;

public record InvocationLexer(Input text) implements Lexer {
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
            var slice = text.slice(start + 1, end)
                    .computeTrimmed();

            var lines = List.<String>createList();
            var buffer = new StringBuilder();
            int depth = 0;
            for (int i = 0; i < slice.length(); i++) {
                var c = slice.charAt(i);
                if (c == ',' && depth == 0) {
                    lines.add(buffer.toString());
                    buffer = new StringBuilder();
                } else {
                    if (c == '(') depth++;
                    if (c == ')') depth--;
                    buffer.append(c);
                }
            }

            lines.add(buffer.toString());

            var arguments = lines
                    .stream()
                    .filter(s -> !s.isBlank())
                    .map(RootText::new)
                    .map(InputNode::new)
                    .foldRight(List.<Node>createList(), List::add);
            var caller = new InputNode(callerText);
            return new Invocation(caller, arguments);
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }
}
