package com.meti.magma;

import com.meti.*;
import com.meti.node.Node;
import com.meti.stage.Rendererer;

import java.util.Optional;
import java.util.stream.Stream;

public class MagmaRenderer implements Rendererer {
    private final Node node;

    public MagmaRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        return Stream.of(
                        new FieldRenderer(node),
                        new IntegerRenderer(node),
                        new InvokeRenderer(node),
                        new StringRenderer(node))
                .map(Rendererer::render)
                .flatMap(Optional::stream)
                .findFirst();
    }
}
