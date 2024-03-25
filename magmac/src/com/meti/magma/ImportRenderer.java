package com.meti.magma;

import com.meti.FieldRenderer;
import com.meti.ImportLexer;
import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public class ImportRenderer implements Renderer {
    private final Node node;

    public ImportRenderer(Node node) {
        super();
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if(!node.is(ImportLexer.ID)) return Optional.empty();

        var parentName = node.apply(ImportLexer.PARENT)
                .flatMap(Attribute::asString)
                .orElseThrow();

        var childString = node.apply(ImportLexer.CHILD)
                .flatMap(Attribute::asString)
                .orElseThrow();

        return Optional.of("import " + childString + " from " + parentName + ";");
    }
}
