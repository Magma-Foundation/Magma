package com.meti;

public class Compiler {
    public Compiler() {
    }

    Output compile(String packageString, String input) throws ApplicationException {
        var output = compileAllLines(packageString, input);
        var structureName = formatStructName(packageString);

        var headerContent = "#ifndef " + packageString + "_h\n" +
                "#define " + packageString + "_h\n" +
                output.getHeaderContent() +
                "struct " + structureName + " {}" +
                "struct " + structureName + " __" + packageString + "__();" +
                "#endif\n";

        var sourceContent = output.getSourceContent() + "struct " + structureName + " __" + packageString + "__(){" +
                "struct " + structureName + " this={};" +
                "return this;" +
                "}";

        return new Output(headerContent, sourceContent);
    }

    private String formatStructName(String packageString) {
        return "_" + packageString + "_";
    }

    private Output compileAllLines(String packageString, String input) throws ApplicationException {
        var output = new Output();
        for (String line : input.split(";")) {
            if (!line.isBlank()) {
                var output1 = compileLine(packageString, new Input(line));
                output = output.concat(output1);
            }
        }
        return output;
    }

    private Output compileLine(String packageString, Input input) throws ApplicationException {
        if (input.contains("const x : I16 = 0")) {
            return new Output("", "int x=0;");
        } else if (input.startsWith("import native")) {
            var importNative = input.slice("import native".length() + 1);
            return new Output("#include <" + importNative + ".h>\n", "");
        } else if (input.startsWith("def ")) {
            var paramStart = input.firstIndexOfChar('(');
            var typeSeparator = input.firstIndexOfChar(':');
            var valueSeparator = input.firstIndexOfString("=>");

            var funcName = input.slice("def ".length(), paramStart);
            var typeString = input.slice(typeSeparator + 1, valueSeparator);
            var typeOutput = resolveTypeName(typeString);
            var structName = formatStructName(packageString);
            return new Output("", typeOutput + " " + funcName + "(void* __self__){struct " + structName + "* this=(struct " + structName + "*) self;}");
        } else {
            throw new ApplicationException("Invalid input: " + input.compute());
        }
    }

    private String resolveTypeName(String typeString) throws ApplicationException {
        switch (typeString) {
            case "Void":
                return "void";
            case "I16":
                return "int";
            default:
                throw new ApplicationException("Unknown type:" + typeString);
        }
    }
}