package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.meti.EmptyNode.EmptyNode_;

public record Compiler(Input input) {
    String compile() {
        var linesArray = splitLines(input.value());
        var linesList = new ArrayList<Node>();
        for (String content : linesArray) {
            linesList.add(new Content(content));
        }
        var root = new Block(linesList);
        var withNodes = parseAST(root);
        var output = withNodes.render();
        return output.substring(1, output.length() - 1);
    }

    private Node parseAST(Node root) {
        Node newRoot;
        if (root.group() == Node.Group.Content) {
            newRoot = parseLine(new Input(root.getValue().trim()));
        } else {
            newRoot = root;
        }
        var withChild = parseChild(newRoot);
        return parseChildren(withChild);
    }

    private List<String> splitLines(String parent) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        var parentLength = parent.length();
        for (int i = 0; i < parentLength; i++) {
            var c = parent.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        lines.removeIf(String::isBlank);

        return lines;
    }

    private Node parseChild(Node root) {
        var types = root.streamNodes().collect(Collectors.toList());
        var current = root;
        for (Node.Type type : types) {
            var child = current.apply(type);
            var newChild = parseAST(child);
            current = current.withNode(newChild);
        }
        return current;
    }

    private Node parseChildren(Node withChild) {
        var oldChildren = withChild.streamChildren().collect(Collectors.toList());
        var newChildren = new ArrayList<Node>();
        for (Node oldChild : oldChildren) {
            newChildren.add(parseAST(oldChild));
        }
        return withChild.withNodes(newChildren);
    }

    private String parseField(Input input) {
        var typeSeparator = input.firstIndexOfChar(':');
        var valueSeparator = input.firstIndexOfChar('=');

        var keys = input.slice(0, typeSeparator);
        var name = keys.substring(keys.lastIndexOf(' ') + 1);
        var typeName = input.slice(typeSeparator + 1, valueSeparator == -1
                ? input.length()
                : valueSeparator);
        var type = resolveTypeName(typeName);
        var valueOutput = valueSeparator != -1 ? '=' + input.slice(valueSeparator + 1) : "";
        return type + " " + name + valueOutput;
    }

    private Node parseFunction(Input input) {
        var paramStart = input.firstIndexOfChar('(');
        var paramEnd = input.firstIndexOfChar(')');
        var paramSlice = input.slice(paramStart + 1, paramEnd);
        var parameters = parseParameters(paramSlice);

        var name = input.slice("def ".length(), paramStart);

        var returnTypeString = input.slice(input.lastIndexOfChar() + 1, input.firstIndexOfSlice());
        var returnType = resolveTypeName(returnTypeString);

        var bodyStart = input.firstIndexOfChar('{');
        var body = input.slice(bodyStart, input.length());

        return new Function(name, parameters, returnType, new Content(body));
    }

    private Node parseLine(Input input) {
        if (input.isBlank()) {
            return EmptyNode_;
        } else if (input.startsWith("{") && input.endsWith("}")) {
            return parseBody(input);
        } else if (input.startsWith("const ")) {
            return new Declaration(parseField(input));
        } else if (input.startsWith("def")) {
            return parseFunction(input);
        } else if (input.startsWith("return ")) {
            var value = input.slice("return ".length());
            return new Return(new Content(value));
        } else {
            try {
                var value = Integer.parseInt(input.value());
                return new IntegerNode(value);
            } catch (NumberFormatException e) {
                throw new UnsupportedOperationException("Unknown input: " + input);
            }
        }
    }

    private ArrayList<String> parseParameters(String paramSlice) {
        var paramStrings = paramSlice.split(",");
        var parameters = new ArrayList<String>();
        for (String paramString : paramStrings) {
            if (!paramString.isBlank()) {
                parameters.add(parseField(new Input(paramString)));
            }
        }
        return parameters;
    }

    private String resolveTypeName(String returnTypeString) {
        String returnType;
        if (returnTypeString.equals("Void")) {
            returnType = "void";
        } else if (returnTypeString.equals("I16")) {
            returnType = "int";
        } else {
            returnType = "unsigned int";
        }
        return returnType;
    }

    private Node parseBody(Input input) {
        var start = input.firstIndexOfChar('{') + 1;
        var end = input.length() - 1;

        var body = input.slice(start, end);
        var lines = splitLines(body);

        var children = new ArrayList<Node>();
        for (String line : lines) {
            children.add(new Content(line));
        }

        return new Block(children);
    }
}