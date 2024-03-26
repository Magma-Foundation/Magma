package com.meti.stage;

import com.meti.java.Lexer;
import com.meti.node.Content;
import com.meti.node.Node;

import java.util.Optional;
import java.util.Stack;

public abstract class LexingStage extends Stage<Content, Node> {
    @Override
    public Optional<Node> onEnter(Content value) {
        return createLexer(value).lex();
    }

    protected abstract Lexer createLexer(Content value);

    @Override
    protected Optional<Node> onExit(Node node, Stack<Node> b) {
        return Optional.of(node);
    }

    @Override
    public Optional<Node> applyToNode(Node node, Stack<Node> b) {
        if (node instanceof Content result) {
            return apply(result);
        } else {
            return Optional.of(node);
        }
    }
}
