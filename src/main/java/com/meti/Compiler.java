package com.meti;

public class Compiler {
    private final String packageName;
    private final String input;

    public Compiler(String packageName, String input) {
        this.packageName = packageName;
        this.input = input;
    }

    String compile() {
        var classType = "___" + packageName + "___";
        var structType = "struct " + classType;
        var content = compileContent();
        return structType + "{};" +
               structType + " __" + packageName + "__(){" +
               structType + " this={};" + content + "return this;}";
    }

    private String compileContent() {
        if (input.startsWith("const ")) {
            var name = slice("const ".length(), input.indexOf(':'));
            var value = slice(input.indexOf('=') + 1, input.length() - 1);
            return "int " + name + "=" + value + ";";
        } else {
            return "";
        }
    }

    private String slice(int start, int end) {
        return input.substring(start, end).trim();
    }
}
