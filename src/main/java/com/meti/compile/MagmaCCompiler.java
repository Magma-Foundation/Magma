package com.meti.compile;

import java.util.stream.Collectors;

public record MagmaCCompiler(String input) {
    public String compile() throws CompileException {
        if (input.isBlank()) return input;
        var root = new Input(this.input);
        var node = lexNodeTree(root);
        return new MagmaRenderer(node)
                .render()
                .getInput();
    }

    private static Node lexNodeTree(Input input) throws CompileException {
        var node = lexNode(input);
        var types = node.apply(Attribute.Group.Field).collect(Collectors.toList());
        var current = node;
        for (Attribute.Type type : types) {
            var oldField = current.apply(type).asNode();
            var oldType = oldField.apply(Attribute.Type.Type).asNode()
                    .apply(Attribute.Type.Value)
                    .asInput();
            var newType = lexType(oldType);
            var newField = oldField.with(Attribute.Type.Type, new NodeAttribute(newType));
            current = current.with(type, new NodeAttribute(newField));
        }
        return current;
    }

    private static Node lexNode(Input input) throws LexException {
        if (input.isEmpty()) throw new LexException("Input may not be empty.");
        return new BlockLexer(input).lex()
                .or(new FunctionLexer(input).lex())
                .or(new ReturnLexer(input).lex())
                .or(new IntegerLexer(input).lex())
                .orElseThrow(() -> new LexException("Unknown body: " + input.getInput()));
    }

    private static Node lexType(Input text) throws LexException {
        var isSigned = text.startsWithChar('I');
        var isUnsigned = text.startsWithChar('U');
        if (isSigned || isUnsigned) {
            var bitsText = text.slice(1);
            var bits = Integer.parseInt(bitsText.getInput());
            return new IntegerType(isSigned, bits);
        } else {
            throw new LexException("Cannot lex type: " + text);
        }
    }
}
