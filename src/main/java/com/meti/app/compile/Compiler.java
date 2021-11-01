package com.meti.app.compile;

import com.meti.api.ListStream;
import com.meti.api.StreamException;

import java.util.ArrayList;

public record Compiler(String input) {
    public String compile() throws CompileException {
        if (input.isBlank()) return "";
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }
        lines.add(builder.toString());
        lines.removeIf(String::isBlank);

        try {
            return new ListStream<>(lines)
                    .map(this::compileLine)
                    .foldRight((current, next) -> current + next)
                    .orElse("");
        } catch (StreamException e) {
            throw new CompileException(e);
        }
    }

    private String compileLine(String line) throws CompileException {
        var tree = new MagmaLexingStage(line).lex();
        return new CRenderingStage(tree).render()
                .asString()
                .orElse("");
    }
}