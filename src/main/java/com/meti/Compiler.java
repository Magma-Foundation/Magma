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
        String content;
        if (input.startsWith("const ")) {
            var rawName = input.substring("const ".length(), input.indexOf(':'));
            var name = rawName.trim();
            content = "int " + name + "=10;";
        } else {
            content = "";
        }
        return structType + "{};" +
                 structType + " __" + packageName + "__(){" +
                 structType + " this={};" + content + "return this;}";
    }
}
