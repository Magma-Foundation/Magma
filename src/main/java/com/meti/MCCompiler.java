package com.meti;

import com.meti.option.None;
import com.meti.option.Option;

import java.util.List;

public record MCCompiler(Input input) {
    String compile() throws CompileException {
        var ast = lexInput();
        return render(ast);
    }

    private Node lexInput() throws CompileException {
        var list = List.of(
                new ReturnLexer(input),
                new IntegerLexer(input));
        return this.processAll(list)
                .orElseThrow(() -> new LexException("Invalid input: " + input));
    }

    private <T> Option<T> processAll(List<? extends Processor<T, ?>> list) throws CompileException {
        for (var lexer : list) {
            var option = lexer.process();
            if (option.isPresent()) {
                return option;
            }
        }
        return new None<>();
    }

    private String render(Node node) throws CompileException {
        var renderers = List.of(
                new ReturnRenderer(node),
                new IntegerRenderer(node));
        return this.processAll(renderers)
                .orElseThrow(() -> new RenderException("Unable to render:" + node));
    }

}