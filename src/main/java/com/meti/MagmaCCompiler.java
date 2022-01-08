package com.meti;

public record MagmaCCompiler(String input) {
    private static Node lexNode(String input) {
        if (input.startsWith("def ")) {
            var paramStart = input.indexOf('(');
            var name = slice(input, "def ".length(), paramStart);
            var typeSeparator = input.indexOf(':');
            var valueSeparator = input.indexOf("=>");
            var typeString = slice(input, typeSeparator + 1, valueSeparator);
            String type;
            if (typeString.equals("I16")) {
                type = "int";
            } else {
                type = "unsigned int";
            }
            return new Content(type + " " + name + "(){return 0;}");
        }
        if (input.startsWith("return ")) {
            var valueString = slice(input, "return ".length(), input.length());
            var value = new Content(valueString);
            return new Return(value);
        }
        try {
            Integer.parseInt(input);
            return new Content(input);
        } catch (NumberFormatException e) {
            return new Content("");
        }
    }

    private static Node lexNodeTree(String input) throws AttributeException {
        var ast = lexNode(input);
        if (ast.is(Node.Type.Return)) {
            var value = ast.getValueAsNode();
            var valueString = value.getValueAsString();
            var child = lexNode(valueString);
            return ast.withValue(child);
        }
        return ast;
    }

    private static String slice(String input, int start, int end) {
        return input.substring(start, end).trim();
    }

    String compile() throws CompileException {
        if (input.isBlank()) return input;
        var node = lexNodeTree(input);
        return renderAST(node);
    }

    private String renderAST(Node node) throws CompileException {
        Node withValue;
        if (node.is(Node.Type.Return)) {
            var value = node.getValueAsNode();
            var valueString = renderNode(value);
            withValue = node.withValue(new Content(valueString));
        } else {
            withValue = node;
        }
        return renderNode(withValue);
    }

    private String renderNode(Node node) throws CompileException {
        if (node.is(Node.Type.Return)) {
            var child = node.getValueAsNode();
            var renderedChild = renderAST(child);
            return "return " + renderedChild + ";";
        } else if (node.is(Node.Type.Content)) {
            return node.getValueAsString();
        } else {
            throw new CompileException("Unable to render node:" + node);
        }
    }
}
