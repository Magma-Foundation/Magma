package com.meti;

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
        for (AbstractLexer lexer : list) {
            var option = lexer.lex();
            if (option.isPresent()) {
                return option.orElse(null);
            }
        }
        throw new CompileException("Invalid node: " + input);
    }

    private static String render(Node node) throws RenderException {
        var renderers = List.of(
                new ReturnRenderer(node),
                new IntegerRenderer(node));

        for (Renderer renderer : renderers) {
            var rendered = renderer.render();
            if (rendered.isPresent()) {
                return rendered.orElse(null);
            }
        }
        throw new RenderException("Unable to render:" + node);
    }

}