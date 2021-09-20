package com.meti;

public class Compiler {
    public Compiler() {
    }

    Output compile(Script script, String input) throws ApplicationException {
        var output = compileAllLines(input);
        var packageString = script.stringifyPackage();

        var headerContent = "#ifndef " + packageString + "_h\n" +
                "#define " + packageString + "_h\n" +
                output +
                "struct _" + packageString + "_ {}" +
                "struct _" + packageString + "_ __" + packageString + "__();" +
                "#endif\n";

        var sourceContent = "struct _" + packageString + "_ __" + packageString + "__(){" +
                "struct _" + packageString + "_ this={};" +
                "return this;" +
                "}";

        return new Output(headerContent, sourceContent);
    }

    private String compileAllLines(String input) throws ApplicationException {
        var output = new StringBuilder();
        for (String line : input.split(";")) {
            if (!line.isBlank()) {
                output.append(compileLine(line));
            }
        }
        return output.toString();
    }

    private String compileLine(String line) throws ApplicationException {
        if (line.startsWith("import native")) {
            var importNative = line.substring("import native".length() + 1);
            return "#include <" + importNative + ".h>\n";
        } else {
            throw new ApplicationException("Invalid input: " + line);
        }
    }
}