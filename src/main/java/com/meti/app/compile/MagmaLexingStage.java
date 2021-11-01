package com.meti.app.compile;

import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.magma.MagmaLexer;

import java.util.stream.Collectors;

public record MagmaLexingStage(String line) {
    public static Node lexTree(Node node) throws AttributeException {
        var children = node.stream(Attribute.Group.Node).collect(Collectors.toList());
        for (Attribute.Type child : children) {
            node = lexTree(node.apply(child).asNode());
        }
        return node;
    }

    Node lex() throws CompileException {
        var input = new Input(line());
        var root = new MagmaLexer(input).process();
        return lexTree(root);
    }
}