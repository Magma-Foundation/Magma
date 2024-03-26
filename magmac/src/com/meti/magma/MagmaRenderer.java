package com.meti.magma;

import com.meti.FieldRenderer;
import com.meti.IntegerRenderer;
import com.meti.InvokeRenderer;
import com.meti.StringRenderer;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;
import java.util.stream.Stream;

public class MagmaRenderer implements Renderer {
    private final Node node;

    public MagmaRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        return Stream.of(
                        new DefinitionRenderer(node),
                        new ClassRenderer(node),
                        new FieldRenderer(node),
                        new ImportRenderer(node),
                        new IntegerRenderer(node),
                        new InvokeRenderer(node),
                        new MethodRenderer(node),
                        new StatementRenderer(node),
                        new StringRenderer(node))
                .map(Renderer::render)
                .flatMap(Optional::stream)
                .findFirst();
    }
}
