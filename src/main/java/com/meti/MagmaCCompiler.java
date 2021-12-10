package com.meti;

public class MagmaCCompiler {
    private final String input;

    public MagmaCCompiler(String input) {
        this.input = input;
    }

    private static String compileBlock(Input input) throws CompileException {
        var bodySlice = input.slice(1, input.size() - 1);
        var inputLines = new Splitter(bodySlice).split();
        var output = new StringBuilder();
        for (String inputLine : inputLines) {
            output.append(compileNode(new Input(inputLine)));
        }

        return "{" + output + "}";
    }

    private static String compileFunction(Input input) throws CompileException {
        var parameterStart = input.firstChar('(').orElse(-1);
        var name = input.slice("def ".length(), parameterStart);
        var typeSeparator = input.firstChar(':').orElse(-1);
        var returnSeparator = input.firstSlice().orElse(-1);
        var typeString = input.slice(typeSeparator + 1, returnSeparator);

        var type = resolveTypeName(typeString);
        var bodyString = input.sliceToEnd(returnSeparator + 2);
        var body = MagmaCCompiler.compileNode(bodyString);
        return type + " " + name + "()" + body;
    }

    private static String compileNode(Input input) throws CompileException {
        String output;
        if (input.startsWithSlice("def ")) {
            output = compileFunction(input);
        } else if (input.startsWithChar('{') && input.endsWithChar('}')) {
            output = compileBlock(input);
        } else if (input.startsWithSlice("return ")) {
            var valueString = input.sliceToEnd("return ".length());
            var value = compileNode(valueString);
            output = "return " + value + ";";
        } else {
            try {
                return String.valueOf(Integer.parseInt(input.compute()));
            } catch (NumberFormatException e) {
                throw new CompileException("Cannot compile: " + input.compute());
            }
        }
        return output;
    }

    private static String resolveTypeName(String typeString) throws CompileException {
        return switch (typeString) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> throw new CompileException("Unknown type: " + typeString);
        };
    }

    String compile() throws CompileException {
        var lines = new Splitter(input).split();
        var builder = new StringBuilder();
        for (String line : lines) {
            builder.append(compileNode(new Input(line)));
        }
        return builder.toString();
    }
}
