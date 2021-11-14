package com.meti.app;

import com.meti.api.stream.JavaListStream;
import com.meti.api.stream.StreamException;
import com.meti.app.attribute.Attribute;
import com.meti.app.node.Content;
import com.meti.app.node.Node;
import com.meti.app.split.Splitter;
import com.meti.app.stage.AbstractProcessingStage;
import com.meti.app.stage.CRenderingProcessingStage;
import com.meti.app.stage.MagmaLexingProcessingStage;

import java.util.ArrayList;
import java.util.List;

public record Compiler(String input) {
    String compile() throws CompileException {
        if (input.isBlank()) return "";
        var nodes = lexInput();
        return renderOutput(nodes);
    }

    private List<Node> lexInput() throws CompileException {
        try {
            return new Splitter(new Input(input)).split()
                    .map(Content::new)
                    .map(MagmaLexingProcessingStage::new)
                    .map(AbstractProcessingStage::process)
                    .foldRight(new ArrayList<>(), (nodes, node) -> {
                        nodes.add(node);
                        return nodes;
                    });
        } catch (StreamException e) {
            throw new CompileException("Cannot lex into AST.", e);
        }
    }

    private String renderOutput(List<Node> nodes) throws CompileException {
        try {
            return new JavaListStream<>(nodes)
                    .map(CRenderingProcessingStage::new)
                    .map(AbstractProcessingStage::process)
                    .map(value -> value.apply(Attribute.Type.Value))
                    .map(Attribute::asString)
                    .foldRight((sum, next) -> sum + next)
                    .orElse("");
        } catch (StreamException e) {
            throw new CompileException("Cannot render AST.", e);
        }
    }
}
