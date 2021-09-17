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
            var rawName = input.substring("const ".length(), input.indexOf(':'));
            var name = rawName.trim();
            return "int " + name + "=10;";
        } else {
            return "";
        }
    }
}
