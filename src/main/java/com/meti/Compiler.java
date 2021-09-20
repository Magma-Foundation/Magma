package com.meti;

public class Compiler {
    private final String name;

    public Compiler(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    Output compile(String input) throws ApplicationException {
        var outputContent = new StringBuilder();
        for (String line : input.split(";")) {
            if (!line.isBlank()) {
                outputContent.append(compileLine(line));
            }
        }

        var headerContent = createHeaderOutput(outputContent.toString());
        var sourceContent = createSourceOutput();
        return new Output(headerContent, sourceContent);
    }

    private String compileLine(String line) throws ApplicationException {
        if (line.startsWith("import native")) {
            var importNative = line.substring("import native".length() + 1);
            return "#include <" + importNative + ".h>\n";
        } else {
            throw new ApplicationException("Invalid input: " + line);
        }
    }

    private String createSourceOutput() {
        return "struct _" + getName() + "_ __" + getName() + "__(){" +
                "struct _" + getName() + "_ this={};" +
                "return this;" +
                "}";
    }

    private String createHeaderOutput(String outputContent) {
        return "#ifndef " + getName() + "_h\n" +
                "#define " + getName() + "_h\n" +
                outputContent +
                "struct _" + getName() + "_ {}" +
                "struct _" + getName() + "_ __" + getName() + "__();" +
                "#endif\n";
    }
}
