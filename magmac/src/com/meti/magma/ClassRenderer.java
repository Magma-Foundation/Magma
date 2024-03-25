package com.meti.magma;

import com.meti.ClassNode;
import com.meti.java.ClassLexer;
import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public class ClassRenderer implements Renderer {
    private final Node node;

    public ClassRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if(!node.is(ClassLexer.ID)) return Optional.empty();

        var flags = node.apply(ClassLexer.FLAGS).flatMap(Attribute::asListOfStrings).orElseThrow();
        var body = node.apply(ClassLexer.BODY).flatMap(Attribute::asNode)
                .flatMap(node -> node.apply(Content.VALUE))
                .flatMap(Attribute::asString);

        var name = node.apply(ClassLexer.NAME).flatMap(Attribute::asString).orElseThrow();
        var indent = node.apply(ClassLexer.INDENT).flatMap(Attribute::asInt).orElseThrow();

        var flagString = flags.contains("public") ? "export " : "";
        String bodyString;
        if (body.isEmpty()) {
            bodyString = "{}";
        } else {
            bodyString = "{" + body.orElseThrow() + "\n" + "\t".repeat(indent) + "}";
        }
        return Optional.of(flagString + "class def " + name + "() => " + bodyString);
    }
}
