package com.meti;

import com.meti.option.None;
import com.meti.option.Option;

import java.util.List;

public record MCCompiler(Input input) {
    String compile() throws CompileException {
        var ast = lexInput();
        return renderOutput(ast);
    }

    private Node lexInput() throws CompileException {
        var node = lexInput(input);
        return node.getValueAsNode()
                .flatMap(Node::getValueAsInput)
                .map(this::lexInput)
                .map(node::with)
                .orElse(node);
    }

    private String renderOutput(Node node) throws CompileException {
        var parent = node.getValueAsNode()
                .map(this::renderNode)
                .map(RootInput::new)
                .map(Content::new)
                .map(node::with)
                .orElse(node);
        return renderNode(parent);
    }

    private Node lexInput(Input input) throws CompileException {
        var list = List.of(
                new ReturnLexer(input),
                new IntegerLexer(input));
        return this.processAll(list)
                .orElseThrow(() -> new LexException("Invalid input: " + input.compute()));
    }

    private String renderNode(Node node) throws CompileException {
        var renderers = List.of(
                new ReturnRenderer(node),
                new IntegerRenderer(node));
        return this.processAll(renderers)
                .orElseThrow(() -> new RenderException("Unable to render: " + node));
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

}