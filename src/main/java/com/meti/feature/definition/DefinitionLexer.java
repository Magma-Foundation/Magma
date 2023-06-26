package com.meti.feature.definition;

import com.meti.feature.Lexer;
import com.meti.feature.Node;
import com.meti.safe.NativeString;
import com.meti.safe.option.Option;

public record DefinitionLexer(NativeString input) implements Lexer {
    static Option<Node> lexHelper(NativeString range) {
        return range.firstIndexOfChar('=').map(valueSeparator -> {
            var split = range.splitExcludingAt(valueSeparator);
            var definition = split.left().strip();
            var value = split.right().strip();
            return definition.firstIndexOfChar(':').map(definition::splitExcludingAt).<ImplicitDefinition>map(tuple -> {
                var name = tuple.left().strip();
                var actualType = tuple.right().strip();

                return new ExplicitDefinition(name, actualType, value);
            }).unwrapOrElseGet(() -> new ImplicitDefinition(definition, value));
        });
    }

    @Override
    public Option<Node> lex() {
        return input().firstIndexAfterSlice(NativeString.from("let"))
                .map(input()::sliceFrom)
                .flatMap(DefinitionLexer::lexHelper);
    }
}