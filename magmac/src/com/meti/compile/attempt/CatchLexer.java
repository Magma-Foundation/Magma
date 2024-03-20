package com.meti.compile.attempt;

import com.meti.collect.option.Option;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.collect.stream.Streams;
import com.meti.compile.Lexer;
import com.meti.compile.TypeCompiler;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import static com.meti.collect.option.Options.$$;
import static com.meti.collect.option.Options.$Option;

public class CatchLexer implements Lexer {
    private final JavaString input;
    private final int indent;

    public CatchLexer(JavaString input, int indent) {
        this.input = input;
        this.indent = indent;
    }

    private Option<Node> lex0() {
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
                    .map(JavaString::strip)
                    .map(type -> {
                        TypeCompiler typeCompiler = new TypeCompiler(type.inner());
                        return typeCompiler.compile().map(JavaString::inner).map(JavaString::from);
                    })
                    .collect(Collectors.required(Collectors.toNativeList()))
                    .$();

            var exceptionName = args.sliceFrom(separator.next().$());
            return new CatchNode(exceptionTypes, exceptionName, new Content(input.sliceFrom(blockStart), indent), indent);
        });
    }

    @Override
    public Stream<Node> lex() {
        return Streams.fromOption(lex0());
    }
}
