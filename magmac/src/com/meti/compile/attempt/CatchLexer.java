package com.meti.compile.attempt;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public class CatchLexer implements Lexer {
    private final JavaString input;

    public CatchLexer(JavaString input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        return $Option(() -> {
            var index = input.firstIndexOfSlice("catch").$();
            if (!index.isStart()) $$();

            var blockStart = input.firstIndexOfChar('{').$();
            var prefix = input.sliceTo(blockStart);
            var start = prefix.firstIndexOfChar('(').$();
            var end = prefix.lastIndexOfChar(')').$();
            var args = prefix.sliceBetween(start.next().$().to(end).$());
            var separator = args.lastIndexOfChar(' ').$();
            var exceptionTypes = args.sliceTo(separator)
                    .split("\\|")
                    .map(type -> new TypeCompiler(type.inner()).compile())
                    .map(JavaString::new)
                    .collect(Collectors.toList());

            var exceptionName = args.sliceFrom(separator.next().$());
            return new CatchNode(exceptionTypes, exceptionName, new Content(input.sliceFrom(blockStart), 0));
        });
    }
}
