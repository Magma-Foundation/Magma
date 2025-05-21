package magma.app;

import magma.api.Tuple2;
import magma.api.Tuple2Impl;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.compile.CompileState;
import magma.app.compile.text.Whitespace;

public class WhitespaceCompiler {
    static Option<Tuple2<CompileState, String>> compileWhitespace(CompileState state, String input) {
        return WhitespaceCompiler.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) -> {
            return new Tuple2Impl<CompileState, String>(tuple.left(), tuple.right().generate());
        });
    }

    static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (Strings.isBlank(input)) {
            return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<Tuple2<CompileState, Whitespace>>();
    }
}