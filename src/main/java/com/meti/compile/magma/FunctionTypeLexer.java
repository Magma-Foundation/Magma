package com.meti.compile.magma;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.Option;

import java.util.Arrays;
import java.util.stream.Collectors;

public record FunctionTypeLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.firstIndexOfChar(')')
                .filter(value -> text.startsWithChar('('))
                .flatMap(this::extract);
    }

    private Option<Node> extract(Integer end) {
        var parameters = Arrays.stream(text.slice(1, end).computeTrimmed().split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(Text::new)
                .map(Content::new)
                .collect(Collectors.<Node>toList());
        return text.firstIndexOfSlice("=>").map(separator -> {
            var returnText = text.slice(separator + "=>".length());
            var returnType = new Content(returnText);
            return new FunctionType(returnType, parameters);
        });
    }
}
