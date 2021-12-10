package com.meti;

public class MagmaCCompiler {
    private final String input;

    public MagmaCCompiler(String input) {
        this.input = input;
    }

    String compile() throws CompileException {
        var lines = new Splitter(input).split();
        var builder = new StringBuilder();
        for (String line : lines) {
            builder.append(compileNode(new Input(line)));
        }
        return builder.toString();
    }

    private String compileNode(Input input) throws CompileException {
        String output;
        if (input.startsWithSlice("def ")) {
            output = compileFunction(input);
        } else if (input.startsWithSlice("{")) {
            output = compileBlock(input);
        } else {
            throw new CompileException("Cannot compile: " + input.getInput());
        }
        return output;
    }

    private String compileFunction(Input input) throws CompileException {
        var parameterStart = input.firstChar('(');
        var name = input.slice("def ".length(), parameterStart);
        var typeSeparator = input.firstChar(':');
        var returnSeparator = input.getInput().indexOf("=>");
        var typeString = input.slice(typeSeparator + 1, returnSeparator);

        var type = resolveTypeName(typeString);
        var body = compileBlock(input);
        return type + " " + name + "()" + body;
    }

    private static String compileBlock(Input input) {
        var bodyStart = input.firstChar('{');
        var bodyEnd = input.firstChar('}');
        var bodySlice = input.slice(bodyStart + 1, bodyEnd);
        var lines = bodySlice.equals("return 0;") ? "return 0;" : "";
        return "{" + lines + "}";
    }

    private String resolveTypeName(String typeString) throws CompileException {
        return switch (typeString) {
            case "I16" -> "int";
            case "U16" -> "unsigned int";
            case "Void" -> "void";
            default -> throw new CompileException("Unknown type: " + typeString);
        };
    }
}
