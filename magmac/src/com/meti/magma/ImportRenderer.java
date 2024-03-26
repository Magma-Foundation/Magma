package com.meti.magma;

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
        if (!node.is("import")) return Optional.empty();

        var segments = node.apply("segment")
                .flatMap(Attribute::asListOfStrings)
                .orElseThrow();

        var parentName = String.join(".", segments.subList(0, segments.size() - 1));
        var childString = segments.get(segments.size() - 1);

        var s = (node.apply("static").isPresent() && childString.equals("*"))
                ? "*"
                : "{ " + childString + " }";

        return Optional.of("import " + s + " from " + parentName + ";");
    }
}
