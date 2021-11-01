package com.meti.app.compile;

import com.meti.api.ListStream;
import com.meti.api.StreamException;
import com.meti.app.compile.split.Splitter;

public record Compiler(String input) {
    public String compile() throws CompileException {
        if (input.isBlank()) return "";
        var lines = new Splitter(input).split();

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