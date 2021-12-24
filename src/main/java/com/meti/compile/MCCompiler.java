package com.meti.compile;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.integer.IntegerLexer;
import com.meti.compile.integer.IntegerRenderer;
import com.meti.compile.returns.ReturnLexer;
import com.meti.compile.returns.ReturnRenderer;
import com.meti.option.None;
import com.meti.option.Option;

import java.util.List;

public record MCCompiler(Input input) {
    public String compile() throws CompileException {
        if (input.length() == 0) return "";
        var ast = lexInput();
        return renderOutput(ast);
    }

    private Node lexInput() throws CompileException {
        var node = lexInput(input);
        return node.getValue().map(Attribute::asNode)
                .flatMap(node1 -> node1.getValue().map(Attribute::asInput))
                .map(this::lexInput)
                .map(node::with)
                .orElse(node);
    }

    private String renderOutput(Node node) throws CompileException {
        var parent = node.getValue().map(Attribute::asNode)
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