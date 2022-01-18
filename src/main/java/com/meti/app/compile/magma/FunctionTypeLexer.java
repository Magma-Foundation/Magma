package com.meti.app.compile.magma;

import com.meti.api.option.Option;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

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
