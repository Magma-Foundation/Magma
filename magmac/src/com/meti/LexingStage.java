package com.meti;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;

import java.util.Optional;

public class LexingStage extends Stage<String, Node> {

    @Override
    public Optional<Node> onEnter(String value) {
        return new JavaLexer(value).lex();
    }

    @Override
    protected Node createOutput(Node o) {
        return o;
    }

    @Override
    protected Optional<Node> onExit(Node node) {
        return Optional.of(node);
    }

    @Override
    protected String createInput(Node node) {
        return node.apply(Content.VALUE).flatMap(Attribute::asString).orElseThrow();
    }
}