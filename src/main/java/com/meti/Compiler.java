package com.meti;

public class Compiler {
    private final String packageName;
    private final String input;

    public Compiler(String packageName, String input) {
        this.packageName = packageName;
        this.input = input;
    }

    String compile() {
        String output;
        if (input.isEmpty()) {
            output =  "";
        } else {
            var classType = "___" + packageName + "___";
            var structType = "struct " + classType;
            var rawName = input.substring("const ".length(), input.indexOf(':'));
            var name = rawName.trim();
            output = structType + "{};" +
                     structType + " __" + packageName + "__(){" +
                     structType + " this={};int " + name + "=10;return this;}";
        }
        return output;
    }
}
