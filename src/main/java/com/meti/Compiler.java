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
                "struct " + structureName + "_ __" + packageString + "__();" +
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
                var output1 = compileLine(packageString, line);
                output = output.concat(output1);
            }
        }
        return output;
    }

    private Output compileLine(String packageString, String line) throws ApplicationException {
        if (line.startsWith("import native")) {
            var importNative = line.substring("import native".length() + 1);
            return new Output("#include <" + importNative + ".h>\n", "");
        } else if (line.startsWith("def ")) {
            var funcName = line.substring("def ".length(), line.indexOf("("));
            var structName = formatStructName(packageString);
            return new Output("", "void " + funcName + "(void* __self__){struct " + structName + "* this=(struct " + structName + "*) self;}");
        } else {
            throw new ApplicationException("Invalid input: " + line);
        }
    }
}