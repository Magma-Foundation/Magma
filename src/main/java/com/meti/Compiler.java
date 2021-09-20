package com.meti;

public class Compiler {
    public Compiler() {
    }

    Output compile(Script script, String input) throws ApplicationException {
        String output = "";
        for (String line : input.split(";")) {
            if (!line.isBlank()) {
                if (line.startsWith("import native")) {
                    var importNative = line.substring("import native".length() + 1);
                    output += "#include <" + importNative + ".h>\n";
                } else {
                    throw new ApplicationException("Invalid input: " + line);
                }
            }
        }

        var headerContent = "#ifndef " + script.extractName() + "_h\n" +
                "#define " + script.extractName() + "_h\n" +
                output +
                "struct _" + script.extractName() + "_ {}" +
                "struct _" + script.extractName() + "_ __" + script.extractName() + "__();" +
                "#endif\n";

        var sourceContent = "struct _" + script.extractName() + "_ __" + script.extractName() + "__(){" +
                "struct _" + script.extractName() + "_ this={};" +
                "return this;" +
                "}";

        return new Output(headerContent, sourceContent);
    }
}