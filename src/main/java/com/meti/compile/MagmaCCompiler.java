package com.meti.compile;

import java.util.stream.Collectors;

public record MagmaCCompiler(String input) {
    private static Node lexNode(Input input) throws LexException {
        if (input.isEmpty()) throw new LexException("Input may not be empty.");
        return new BlockLexer(input).lex()
                .or(new FunctionLexer(input).lex())
                .or(new ReturnLexer(input).lex())
                .or(new IntegerLexer(input).lex())
                .orElseThrow(() -> new LexException("Unknown body: " + input.getInput()));
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

    private Node attachFields(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Field).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var node = root.apply(type).asNode();
            var renderedNode = renderField(node);
            current = current.with(type, new NodeAttribute(new Content(renderedNode)));
        }
        return current;
    }

    private Node attachNodes(Node root) throws CompileException {
        var types = root.apply(Attribute.Group.Node).collect(Collectors.toList());
        var current = root;
        for (Attribute.Type type : types) {
            var node = root.apply(type).asNode();
            var renderedNode = renderNodeTree(node);
            current = current.with(type, new NodeAttribute(new Content(renderedNode)));
        }
        return current;
    }

    public String compile() throws CompileException {
        if (input.isBlank()) return input;
        var root = new Input(this.input);
        var node = lexNodeTree(root);
        return renderNodeTree(node).getInput();
    }

    private Input renderField(Node node) throws AttributeException, RenderException {
        var name = node.apply(Attribute.Type.Name).asInput();
        var type = node.apply(Attribute.Type.Type).asNode();

        if (type.is(Node.Type.Integer)) {
            var isSigned = type.apply(Attribute.Type.Sign).asBoolean();
            var bits = type.apply(Attribute.Type.Bits).asInteger();
            String suffix;
            if (bits == 16) suffix = "int";
            else throw new RenderException("Unknown bit quantity: " + bits);
            String value = (isSigned ? "" : "unsigned ") + suffix + " " + name.getInput();
            return new Input(value);
        } else {
            throw new RenderException("Cannot render type: " + type);
        }
    }

    private String renderNode(Node node) throws CompileException {
        if (node.is(Node.Type.Function)) {
            var identity = node.apply(Attribute.Type.Identity).asNode();
            var text = identity.apply(Attribute.Type.Value)
                    .asInput()
                    .getInput();
            var value = node.apply(Attribute.Type.Value).asNode();
            var renderedValue = value.apply(Attribute.Type.Value).asInput().getInput();
            return text + "()" + renderedValue;
        }
        if (node.is(Node.Type.Block)) {
            var builder = new StringBuilder().append("{");
            var children = node.apply(Attribute.Type.Children).streamNodes().collect(Collectors.toList());
            for (Node node1 : children) {
                builder.append(node1.apply(Attribute.Type.Value).asInput().getInput());
            }
            return builder.append("}").toString();
        }
        if (node.is(Node.Type.Return)) {
            var child = node.apply(Attribute.Type.Value).asNode();
            var renderedChild = child.apply(Attribute.Type.Value).asInput().getInput();
            return "return " + renderedChild + ";";
        } else if (node.is(Node.Type.Content)) {
            return node.apply(Attribute.Type.Value).asInput().getInput();
        } else {
            throw new CompileException("Unable to render node:" + node);
        }
    }

    private Input renderNodeTree(Node root) throws CompileException {
        var withFields = attachFields(root);
        var withNodes = attachNodes(withFields);
        return new Input(renderNode(withNodes));
    }
}
