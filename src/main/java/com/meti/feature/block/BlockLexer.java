package com.meti.feature.block;

import com.meti.feature.Content;
import com.meti.feature.Node;
import com.meti.feature.Lexer;
import com.meti.safe.Index;
import com.meti.safe.NativeString;
import com.meti.safe.iter.Collectors;
import com.meti.safe.option.Option;

public record BlockLexer(NativeString input) implements Lexer {
    @Override
    public Option<Node> lex() {
        return input().firstIndexOfChar('{')
                .flatMap(Index::next)
                .flatMap(index -> input().lastIndexOfChar('}').flatMap(index::to))
                .map(input()::slice)
                .map(slice -> {
                    return new Block(slice.splitExcludingAtAll(";")
                            .map(NativeString::strip)
                            .filter(NativeString::isNonEmpty)
                            .map(Content::new)
                            .collect(Collectors.toList()));
                });
    }
}